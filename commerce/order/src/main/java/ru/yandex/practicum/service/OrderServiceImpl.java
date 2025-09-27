package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.DeliveryDto;
import ru.yandex.practicum.commerce.dto.OrderDto;
import ru.yandex.practicum.commerce.dto.PaymentDto;
import ru.yandex.practicum.commerce.enums.OrderState;
import ru.yandex.practicum.commerce.exception.NoOrderFoundException;
import ru.yandex.practicum.commerce.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.commerce.request.CreateNewDeliveryRequest;
import ru.yandex.practicum.commerce.request.CreateNewOrderRequest;
import ru.yandex.practicum.commerce.request.ProductReturnRequest;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.model.DeliveryDetails;
import ru.yandex.practicum.model.OrderEntity;
import ru.yandex.practicum.model.OrderProduct;
import ru.yandex.practicum.repository.OrderRepository;
import ru.yandex.practicum.service.client.DeliveryClientService;
import ru.yandex.practicum.service.client.PaymentClientService;
import ru.yandex.practicum.service.client.WarehouseClientOrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final WarehouseClientOrderService warehouseClientService;
    private final PaymentClientService paymentClientService;
    private final DeliveryClientService deliveryClientService;

    @Override
    @Transactional
    public OrderDto createNewOrder(String username, CreateNewOrderRequest request) {
        checkUsername(username);

        OrderEntity order = orderMapper.toEntity(request, username);
        order.setOrderId(UUID.randomUUID().toString());

        BookedProductsDto bookedProductsDto = getBookedProduct(request);
        createDeliveryDetails(bookedProductsDto, order);

        BigDecimal productPrice = getProductPrice(order);
        order.setProductPrice(productPrice);

        planDelivery(order);

        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getUserOrders(String username) {
        checkUsername(username);
        return orderMapper.toDtoList(orderRepository.findByUsername(username));
    }

    @Override
    @Transactional
    public OrderDto returnProducts(ProductReturnRequest request) {
        OrderEntity order = getOrderById(request.getOrderId());

        Map<String, Integer> products = request.getProducts();
        returnBookedProducts(products);
        setProductsAfterReturn(order, products);

        order.setOrderState(OrderState.PRODUCT_RETURNED);
        order = orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderDto payOrder(String orderId) {
        OrderEntity order = getOrderById(orderId);

        PaymentDto payment = createPayment(order);
        order.setPaymentId(payment.getPaymentId());
        order.setOrderState(OrderState.ON_PAYMENT);

        order = orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderDto successfulPayment(String orderId) {
        OrderEntity order = getOrderById(orderId);
        order.setOrderState(OrderState.PAID);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderDto paymentFailed(String orderId) {
        OrderEntity order = getOrderById(orderId);
        order.setOrderState(OrderState.PAYMENT_FAILED);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderDto successfulDelivery(String orderId) {
        OrderEntity order = getOrderById(orderId);
        order.setOrderState(OrderState.DELIVERED);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderDto deliveryFailed(String orderId) {
        OrderEntity order = getOrderById(orderId);
        order.setOrderState(OrderState.DELIVERY_FAILED);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderDto completeOrder(String orderId) {
        OrderEntity order = getOrderById(orderId);
        order.setOrderState(OrderState.COMPLETED);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderDto calculateTotal(String orderId) {
        OrderEntity order = getOrderById(orderId);

        BigDecimal totalCost = getTotalPrice(order);
        order.setTotalPrice(totalCost);

        order = orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderDto calculateDelivery(String orderId) {
        OrderEntity order = getOrderById(orderId);

        BigDecimal deliveryPrice = getDeliveryPrice(order);
        DeliveryDetails details = order.getDeliveryDetails();
        details.setDeliveryPrice(deliveryPrice);

        order = orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderDto assembleOrder(String orderId) {
        OrderEntity order = getOrderById(orderId);

        assembleProducts(order);
        order.setOrderState(OrderState.ASSEMBLED);
        orderRepository.save(order);

        return orderMapper.toDto(order);
    }

    @Override
    @Transactional
    public OrderDto assemblyFailed(String orderId) {
        OrderEntity order = getOrderById(orderId);
        order.setOrderState(OrderState.ASSEMBLY_FAILED);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    private void checkUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new NotAuthorizedUserException("The user is not logged in");
        }
    }

    private OrderEntity getOrderById(String orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new NoOrderFoundException("Order is not found."));
    }

    private void setProductsAfterReturn(OrderEntity order, Map<String, Integer> returnedProducts) {
        List<OrderProduct> products = order.getProducts();
        for (OrderProduct product : products) {
            int returnQuantity = returnedProducts.get(product.getProductId());
            int totalQuantity = product.getQuantity() - returnQuantity;
            if (totalQuantity < 0) {
                product.setQuantity(0);
            }
            product.setQuantity(product.getQuantity() - returnQuantity);
        }
        order.setProducts(products);
    }

    private void planDelivery(OrderEntity order) {

        DeliveryDto deliveryDto = createDelivery(order);

        DeliveryDetails deliveryDetails = order.getDeliveryDetails();
        deliveryDetails.setDeliveryId(deliveryDto.getDeliveryId());

        order.setOrderState(OrderState.ON_DELIVERY);
    }

    private DeliveryDto createDelivery(OrderEntity order) {
        CreateNewDeliveryRequest request = new CreateNewDeliveryRequest();
        request.setOrderDto(orderMapper.toDto(order));
        request.setFromAddress(warehouseClientService.getWarehouseAddress());
        request.setToAddress(orderMapper.toDto(order.getDeliveryAddress()));
        return deliveryClientService.planDelivery(request);
    }

    private void returnBookedProducts(Map<String, Integer> products) {
        warehouseClientService.fetchReturnBookedProducts(products);
    }

    private PaymentDto createPayment(OrderEntity order) {
        OrderDto orderDto = orderMapper.toDto(order);
        return paymentClientService.fetchCreatePayment(orderDto);
    }

    private BigDecimal getProductPrice(OrderEntity order) {
        OrderDto orderDto = orderMapper.toDto(order);
        return paymentClientService.fetchGetProductCost(orderDto);
    }

    private BigDecimal getDeliveryPrice(OrderEntity order) {
        return deliveryClientService.deliveryCost(order.getDeliveryDetails().getDeliveryId());
    }

    private BigDecimal getTotalPrice(OrderEntity order) {
        OrderDto orderDto = orderMapper.toDto(order);
        return paymentClientService.fetchGetTotalCost(orderDto);
    }

    private void createDeliveryDetails(BookedProductsDto bookedProducts, OrderEntity order) {
        DeliveryDetails deliveryDetails = new DeliveryDetails();
        deliveryDetails.setDeliveryVolume(bookedProducts.getDeliveryVolume());
        deliveryDetails.setDeliveryWeight(bookedProducts.getDeliveryWeight());
        deliveryDetails.setFragile(bookedProducts.isFragile());
        order.setDeliveryDetails(deliveryDetails);
    }

    private void assembleProducts(OrderEntity order) {
        AssemblyProductsForOrderRequest request = new AssemblyProductsForOrderRequest();
        Map<String, Integer> products = orderMapper.toDtoMap(order.getProducts());
        request.setProducts(products);
        request.setOrderId(order.getOrderId());
        warehouseClientService.assemblyProducts(request);
    }

    private BookedProductsDto getBookedProduct(CreateNewOrderRequest request) {
        return warehouseClientService.fetchCheckProductsQuantity(request.getShoppingCart());
    }

}
