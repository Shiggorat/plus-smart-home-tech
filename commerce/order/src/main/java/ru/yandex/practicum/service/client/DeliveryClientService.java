package ru.yandex.practicum.service.client;

import ru.yandex.practicum.commerce.dto.DeliveryDto;
import ru.yandex.practicum.commerce.request.CreateNewDeliveryRequest;

import java.math.BigDecimal;

public interface DeliveryClientService {
    DeliveryDto planDelivery(CreateNewDeliveryRequest request);

    BigDecimal deliveryCost(String deliveryId);
}
