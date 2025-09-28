package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.dto.OrderDto;
import ru.yandex.practicum.commerce.dto.PaymentDto;
import ru.yandex.practicum.commerce.exception.NoPaymentFoundException;
import ru.yandex.practicum.commerce.exception.NotEnoughInfoInOrderToCalculateException;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.model.PaymentEntity;
import ru.yandex.practicum.model.PaymentState;
import ru.yandex.practicum.repository.PaymentRepository;
import ru.yandex.practicum.service.client.OrderClientPaymentService;
import ru.yandex.practicum.service.client.ShoppingStoreClientPaymentService;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper paymentMapper;
    private final ShoppingStoreClientPaymentService shoppingClientStoreService;
    private final OrderClientPaymentService orderClientService;

    @Override
    @Transactional
    public PaymentDto createPayment(OrderDto order) {
        PaymentEntity payment = paymentMapper.toEntity(order);
        ensurePricesArePresent(payment.getProductPrice(), payment.getDeliveryPrice());
        if (payment.getTotalPrice() == null) {
            throw new NotEnoughInfoInOrderToCalculateException("There is not enough information in the " +
                    "order to calculate: there is no information about total cost");
        }
        payment.setPaymentId(UUID.randomUUID().toString());
        payment = repository.save(payment);
        BigDecimal feeTotal = calculateFee(payment.getProductPrice());
        return paymentMapper.toDto(payment, feeTotal);
    }

    @Override
    @Transactional
    public BigDecimal getTotalCost(OrderDto order) {
        BigDecimal deliveryPrice = order.getDeliveryPrice();
        BigDecimal productPrice = order.getProductPrice();
        ensurePricesArePresent(productPrice, deliveryPrice);
        BigDecimal fee = calculateFee(productPrice);
        return deliveryPrice.add(productPrice).add(fee);
    }

    @Override
    @Transactional
    public void successfulPayment(String paymentId) {
        PaymentEntity payment = getPaymentById(paymentId);
        payment.setPaymentState(PaymentState.SUCCESS);
        repository.save(payment);
        orderClientService.successfulPayment(payment.getOrderId());
    }

    @Override
    @Transactional
    public void failedPayment(String paymentId) {
        PaymentEntity payment = getPaymentById(paymentId);
        payment.setPaymentState(PaymentState.FAILED);
        repository.save(payment);
        orderClientService.failedPayment(payment.getOrderId());
    }

    @Override
    public BigDecimal getProductCost(OrderDto order) {
        Map<String, Integer> products = order.getProducts();
        Map<String, BigDecimal> priceById = shoppingClientStoreService.fetchGetProductsPrice(
                products.keySet().stream().toList()
        );

        BigDecimal productsCost = BigDecimal.ZERO;

        for (Map.Entry<String, BigDecimal> entry : priceById.entrySet()) {
            int quantity = products.get(entry.getKey());
            BigDecimal price = entry.getValue();
            BigDecimal sumPrice = price.multiply(BigDecimal.valueOf(quantity));
            productsCost = productsCost.add(sumPrice);
        }

        return productsCost;
    }

    private BigDecimal calculateFee(BigDecimal productPrice) {
        return productPrice.multiply(BigDecimal.valueOf(0.10));
    }

    private PaymentEntity getPaymentById(String paymentId) {
        return repository.findById(paymentId).orElseThrow(() ->
                new NoPaymentFoundException("Payment was not found"));
    }

    private void ensurePricesArePresent(BigDecimal productPrice, BigDecimal deliveryPrice) {
        if (deliveryPrice == null) {
            throw new NotEnoughInfoInOrderToCalculateException("There is not enough information in the " +
                    "order to calculate: there is no information about delivery");
        }
        if (productPrice == null) {
            throw new NotEnoughInfoInOrderToCalculateException("There is not enough information in the " +
                    "order to calculate: there is no information about products price");
        }
    }
}
