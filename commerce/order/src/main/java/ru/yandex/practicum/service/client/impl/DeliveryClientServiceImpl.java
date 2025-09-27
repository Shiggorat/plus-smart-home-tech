package ru.yandex.practicum.service.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.client.DeliveryClient;
import ru.yandex.practicum.commerce.dto.DeliveryDto;
import ru.yandex.practicum.commerce.request.CreateNewDeliveryRequest;
import ru.yandex.practicum.service.client.DeliveryClientService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DeliveryClientServiceImpl implements DeliveryClientService {

    private final DeliveryClient deliveryClient;

    @Override
    public DeliveryDto planDelivery(CreateNewDeliveryRequest request) {
        return deliveryClient.createDelivery(request);
    }

    @Override
    public BigDecimal deliveryCost(String deliveryId) {
        return deliveryClient.calculateDeliveryCost(deliveryId);
    }
}
