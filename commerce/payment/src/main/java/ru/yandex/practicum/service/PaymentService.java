package ru.yandex.practicum.service;

import ru.yandex.practicum.commerce.dto.OrderDto;
import ru.yandex.practicum.commerce.dto.PaymentDto;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentDto createPayment(OrderDto order);

    public BigDecimal getTotalCost(OrderDto order);

    public void successfulPayment(String paymentId);

    public BigDecimal getProductCost(OrderDto order);

    public void failedPayment(String paymentId);
}
