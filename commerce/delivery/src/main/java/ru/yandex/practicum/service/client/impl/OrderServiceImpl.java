package ru.yandex.practicum.service.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.client.OrderClient;
import ru.yandex.practicum.service.client.OrderClientDeliveryService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderClientDeliveryService {

    private final OrderClient orderClient;

    @Override
    public void failedDelivery(String orderId) {
        orderClient.deliveryFailed(orderId);
    }

    @Override
    public void successfulDelivery(String orderId) {
        orderClient.successfulDelivery(orderId);
    }
}
