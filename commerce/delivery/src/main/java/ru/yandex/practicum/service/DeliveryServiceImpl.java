package ru.yandex.practicum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
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
@Slf4j
public class DeliveryServiceImpl implements DeliveryService {

    private static final String WAREHOUSE_ADDRESS_1 = "ADDRESS_1";
    private static final String WAREHOUSE_ADDRESS_2 = "ADDRESS_2";

    private final DeliveryRepository repository;
    private final OrderClientDeliveryService orderClientService;
    private final DeliveryMapper deliveryMapper;
    private final WarehouseClientDeliveryService warehouseClientService;

    @Override
    @Transactional
    public DeliveryDto createDelivery(CreateNewDeliveryRequest request) {
        DeliveryEntity delivery = deliveryMapper.toEntity(request);
        delivery.setDeliveryId(UUID.randomUUID().toString());
        delivery = repository.save(delivery);
        return deliveryMapper.toDto(delivery);
    }

    @Override
    @Transactional
    public void successfulDelivery(String orderId) {
        DeliveryEntity delivery = getByOrderId(orderId);
        delivery.setDeliveryState(DeliveryState.DELIVERED);
        repository.save(delivery);
        orderClientService.successfulDelivery(orderId);
    }

    @Override
    @Transactional
    public void pickProducts(String orderId) {
        DeliveryEntity delivery = getByOrderId(orderId);
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        warehouseClientService.fetchShippedToDelivery(new ShippedToDeliveryRequest(orderId, delivery.getDeliveryId()));
        repository.save(delivery);
    }

    @Override
    @Transactional
    public void failedDelivery(String orderId) {
        DeliveryEntity delivery = getByOrderId(orderId);
        delivery.setDeliveryState(DeliveryState.FAILED);
        repository.save(delivery);
        orderClientService.failedDelivery(orderId);
    }

    @Override
    public BigDecimal calculateDeliveryCost(String deliveryId) {
        DeliveryEntity delivery = repository.findById(deliveryId)
                .orElseThrow(() -> {
                    log.warn("Delivery not found for ID: {}", deliveryId);
                    return new NoDeliveryFoundException("Delivery was not found.");
                });

        log.info("Calculating delivery cost for delivery ID: {}, order ID: {}", deliveryId, delivery.getOrderId());

        BigDecimal price = BigDecimal.valueOf(5.0);
        log.debug("Base price: {}", price);

        String fromStreet = delivery.getFromAddress().getStreet();
        String toStreet = delivery.getToAddress().getStreet();
        log.info("From address: {}, To address: {}", fromStreet, toStreet);

        if (fromStreet.contains(WAREHOUSE_ADDRESS_2)) {
            BigDecimal oldPrice = price;
            price = price.add(price.multiply(BigDecimal.valueOf(2)));
            log.info("Warehouse address 2 detected. Price increased from {} to {}", oldPrice, price);
        } else if (fromStreet.contains(WAREHOUSE_ADDRESS_1)) {
            BigDecimal oldPrice = price;
            price = price.add(price);
            log.info("Warehouse address 1 detected. Price increased from {} to {}", oldPrice, price);
        } else {
            log.info("No warehouse address match. No additional cost for address.");
        }

        if (delivery.isFragile()) {
            BigDecimal fragileIncrement = price.multiply(BigDecimal.valueOf(0.2));
            price = price.add(fragileIncrement);
            log.info("Fragile item detected. Additional cost: {}, total: {}", fragileIncrement, price);
        }

        BigDecimal weightCost = BigDecimal.valueOf(delivery.getDeliveryWeight()).multiply(BigDecimal.valueOf(0.3));
        log.info("Delivery weight: {}, cost added: {}", delivery.getDeliveryWeight(), weightCost);
        price = price.add(weightCost);

        BigDecimal volumeCost = BigDecimal.valueOf(delivery.getDeliveryVolume()).multiply(BigDecimal.valueOf(0.2));
        log.info("Delivery volume: {}, cost added: {}", delivery.getDeliveryVolume(), volumeCost);
        price = price.add(volumeCost);

        if (!fromStreet.equals(toStreet)) {
            BigDecimal crossStreetIncrement = price.multiply(BigDecimal.valueOf(0.2));
            price = price.add(crossStreetIncrement);
            log.info("Different streets detected. Additional cost: {}, total: {}", crossStreetIncrement, price);
        } else {
            log.info("Same street for from and to addresses. No cross street additional cost.");
        }

        log.info("Final calculated delivery cost for delivery ID: {}, order ID: {}: {}", deliveryId, delivery.getOrderId(), price);
        return price;
    }

    private DeliveryEntity getByOrderId(String orderId) {
        return repository.findByOrderId(orderId).orElseThrow(() ->
                new NoDeliveryFoundException("Delivery was not found."));
    }
}
