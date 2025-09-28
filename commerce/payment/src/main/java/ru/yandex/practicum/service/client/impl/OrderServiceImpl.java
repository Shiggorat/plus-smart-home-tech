package ru.yandex.practicum.service.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.client.OrderClient;
import ru.yandex.practicum.commerce.dto.OrderDto;
import ru.yandex.practicum.service.client.OrderClientPaymentService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderClientPaymentService {
    private final OrderClient orderClient;

    @Override
    public OrderDto successfulPayment(String orderId) {
        return orderClient.successfulPayment(orderId);
    }

    @Override
    public OrderDto failedPayment(String orderId) {
        return orderClient.paymentFailed(orderId);
    }
}
