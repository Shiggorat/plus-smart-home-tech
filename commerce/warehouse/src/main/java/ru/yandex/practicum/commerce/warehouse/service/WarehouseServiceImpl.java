package ru.yandex.practicum.commerce.warehouse.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.dto.AddressDto;
import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.exception.NoSpecifiedProductInWarehouseException;
import ru.yandex.practicum.commerce.exception.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.commerce.exception.SpecifiedProductAlreadyInWarehouseException;
import ru.yandex.practicum.commerce.request.AddProductUnitRequest;
import ru.yandex.practicum.commerce.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.commerce.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.commerce.request.ShippedToDeliveryRequest;
import ru.yandex.practicum.commerce.warehouse.mapper.WarehouseProductMapper;
import ru.yandex.practicum.commerce.warehouse.model.Dimension;
import ru.yandex.practicum.commerce.warehouse.model.OrderBooking;
import ru.yandex.practicum.commerce.warehouse.model.WarehouseProduct;
import ru.yandex.practicum.commerce.warehouse.repository.OrderBookingRepository;
import ru.yandex.practicum.commerce.warehouse.repository.ProductRepository;

import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private static final String[] ADDRESSES =
            new String[]{"ADDRESS_1", "ADDRESS_2"};
    private static final String CURRENT_ADDRESS =
            ADDRESSES[Random.from(new SecureRandom()).nextInt(0, 1)];
    private final WarehouseProductMapper mapper;
    private final ProductRepository productRepository;
    private final OrderBookingRepository orderBookingRepository;

    @Override
    public void createProduct(NewProductInWarehouseRequest request) {
        if (productRepository.existsByProductId(request.getProductId())) {
            throw new SpecifiedProductAlreadyInWarehouseException
                    ("The product with this description has already been registered in the warehouse.");
        }
        WarehouseProduct warehouseProduct = mapper.toEntity(request);
        productRepository.save(warehouseProduct);
    }

    @Override
    @Transactional
    public BookedProductsDto checkProductsQuantity(ShoppingCartDto cart) {
        Set<String> productIds = cart.getProducts().keySet();
        List<WarehouseProduct> warehouseProducts = productRepository.findByProductIdIn(productIds);
        aggregateProductsQuantity(warehouseProducts, cart.getProducts());
        return aggregateBookedProducts(cart.getProducts(), warehouseProducts);
    }

    @Override
    public void addProductQuantity(AddProductUnitRequest request) {
        WarehouseProduct warehouseProduct = productRepository.findById(request.getProductId()).orElseThrow(() ->
                new NoSpecifiedProductInWarehouseException("There is no information about the product in warehouse."));
        int total = warehouseProduct.getQuantity() + request.getQuantity();
        warehouseProduct.setQuantity(total);
        productRepository.save(warehouseProduct);
    }

    @Override
    public AddressDto getAddress() {
        return AddressDto.builder()
                .country(CURRENT_ADDRESS)
                .city(CURRENT_ADDRESS)
                .street(CURRENT_ADDRESS)
                .house(CURRENT_ADDRESS)
                .flat(CURRENT_ADDRESS)
                .build();
    }

    @Override
    public void returnBookedProducts(Map<String, Integer> returnedProducts) {
        List<WarehouseProduct> warehouseProducts = productRepository.findByProductIdIn(returnedProducts.keySet());
        for (WarehouseProduct product : warehouseProducts) {
            int actualQuantity = product.getQuantity();
            int quantityForReturn = returnedProducts.get(product.getProductId());
            product.setQuantity(actualQuantity + quantityForReturn);
        }
        productRepository.saveAll(warehouseProducts);
    }

    @Override
    public BookedProductsDto assemblyProducts(AssemblyProductsForOrderRequest request) {
        List<WarehouseProduct> warehouseProducts = productRepository.findByProductIdIn(request.getProducts().keySet());

        aggregateProductsQuantity(warehouseProducts, request.getProducts());
        productRepository.saveAll(warehouseProducts);

        createOrderBooking(request);

        return aggregateBookedProducts(request.getProducts(), warehouseProducts);
    }

    @Override
    public void shipProducts(ShippedToDeliveryRequest request) {
        orderBookingRepository.findByOrderId(request.getOrderId())
                .ifPresent(booking -> {
                    booking.setDeliveryId(request.getDeliveryId());
                    orderBookingRepository.save(booking);
                });
    }

    private BookedProductsDto aggregateBookedProducts(Map<String, Integer> pickedProducts, List<WarehouseProduct> warehouseProducts) {
        float volume = 0;
        float weight = 0;
        boolean fragile = false;
        for (WarehouseProduct product : warehouseProducts) {
            int quantity = pickedProducts.get(product.getProductId());
            volume += getVolume(product) * quantity;
            if (!fragile) {
                fragile = product.isFragile();
            }
            weight += product.getWeight() * quantity;
        }
        return new BookedProductsDto(weight, volume, fragile);
    }

    private float getVolume(WarehouseProduct product) {
        Dimension dimension = product.getDimension();
        return dimension.getHeight() * dimension.getWidth() * dimension.getDepth();
    }

    private void aggregateProductsQuantity(List<WarehouseProduct> warehouseProducts, Map<String, Integer> pickedProducts) {
        for (WarehouseProduct product : warehouseProducts) {
            int quantityInCart = pickedProducts.get(product.getProductId());
            int actualQuantity = product.getQuantity();
            if (actualQuantity < quantityInCart) {
                throw new ProductInShoppingCartLowQuantityInWarehouseException(
                        "The product from the basket is not in the required quantity in warehouse.");
            }
            product.setQuantity(actualQuantity - quantityInCart);
        }
    }

    private void createOrderBooking(AssemblyProductsForOrderRequest request) {
        OrderBooking booking = new OrderBooking();
        booking.setProducts(request.getProducts());
        booking.setOrderId(request.getOrderId());
        orderBookingRepository.save(booking);
    }

}
