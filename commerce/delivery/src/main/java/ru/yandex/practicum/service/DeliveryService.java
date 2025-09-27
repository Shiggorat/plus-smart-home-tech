package ru.yandex.practicum.service;

import ru.yandex.practicum.commerce.dto.DeliveryDto;
import ru.yandex.practicum.commerce.request.CreateNewDeliveryRequest;

import java.math.BigDecimal;

public interface DeliveryService {
    DeliveryDto createDelivery(CreateNewDeliveryRequest request);

    void successfulDelivery(String orderId);

    void pickProducts(String orderId);

    void failedDelivery(String orderId);

    BigDecimal calculateDeliveryCost(String deliveryId);
}
