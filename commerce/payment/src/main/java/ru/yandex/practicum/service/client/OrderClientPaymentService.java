package ru.yandex.practicum.service.client;

import ru.yandex.practicum.commerce.dto.OrderDto;

public interface OrderClientPaymentService {

    OrderDto successfulPayment(String orderId);

    OrderDto failedPayment(String orderId);
}
