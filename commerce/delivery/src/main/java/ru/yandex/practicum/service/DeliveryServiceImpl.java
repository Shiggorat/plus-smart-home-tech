package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.dto.DeliveryDto;
import ru.yandex.practicum.commerce.enums.DeliveryState;
import ru.yandex.practicum.commerce.exception.NoDeliveryFoundException;
import ru.yandex.practicum.commerce.request.CreateNewDeliveryRequest;
import ru.yandex.practicum.commerce.request.ShippedToDeliveryRequest;
import ru.yandex.practicum.mapper.DeliveryMapper;
import ru.yandex.practicum.model.DeliveryEntity;
import ru.yandex.practicum.repository.DeliveryRepository;
import ru.yandex.practicum.service.client.OrderClientDeliveryService;
import ru.yandex.practicum.service.client.WarehouseClientDeliveryService;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private static final String WAREHOUSE_ADDRESS_1 = "ADDRESS_1";
    private static final String WAREHOUSE_ADDRESS_2 = "ADDRESS_2";

    private final DeliveryRepository repository;
    private final OrderClientDeliveryService orderClientService;
    private final DeliveryMapper deliveryMapper;
    private final WarehouseClientDeliveryService warehouseClientService;

    @Override
    public DeliveryDto createDelivery(CreateNewDeliveryRequest request) {
        DeliveryEntity delivery = deliveryMapper.toEntity(request);
        delivery.setDeliveryId(UUID.randomUUID().toString());
        delivery = repository.save(delivery);
        return deliveryMapper.toDto(delivery);
    }

    @Override
    public void successfulDelivery(String orderId) {
        DeliveryEntity delivery = getByOrderId(orderId);
        delivery.setDeliveryState(DeliveryState.DELIVERED);
        repository.save(delivery);
        orderClientService.successfulDelivery(orderId);
    }

    @Override
    public void pickProducts(String orderId) {
        DeliveryEntity delivery = getByOrderId(orderId);
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        warehouseClientService.fetchShippedToDelivery(new ShippedToDeliveryRequest(orderId, delivery.getDeliveryId()));
        repository.save(delivery);
    }

    @Override
    public void failedDelivery(String orderId) {
        DeliveryEntity delivery = getByOrderId(orderId);
        delivery.setDeliveryState(DeliveryState.FAILED);
        repository.save(delivery);
        orderClientService.failedDelivery(orderId);
    }

    @Override
    public BigDecimal calculateDeliveryCost(String deliveryId) {
        DeliveryEntity delivery = repository.findById(deliveryId)
                .orElseThrow(() -> new NoDeliveryFoundException("Delivery was not found."));

        BigDecimal price = BigDecimal.valueOf(5.0);

        if (delivery.getFromAddress().getStreet().contains(WAREHOUSE_ADDRESS_2)) {
            price = price.add(price.multiply(BigDecimal.valueOf(2)));
        } else if (delivery.getFromAddress().getStreet().contains(WAREHOUSE_ADDRESS_1)) {
            price = price.add(price);
        }

        if (delivery.isFragile()) {
            price = price.add(price.multiply(BigDecimal.valueOf(0.2)));
        }

        price = price.add(BigDecimal.valueOf(delivery.getDeliveryWeight()).multiply(BigDecimal.valueOf(0.3)));

        price = price.add(BigDecimal.valueOf(delivery.getDeliveryVolume()).multiply(BigDecimal.valueOf(0.2)));

        if (!delivery.getFromAddress().getStreet().equals(delivery.getToAddress().getStreet())) {
            price = price.add(price.multiply(BigDecimal.valueOf(0.2)));
        }

        return price;
    }

    private DeliveryEntity getByOrderId(String orderId) {
        return repository.findByOrderId(orderId).orElseThrow(() ->
                new NoDeliveryFoundException("Delivery was not found."));
    }
}
