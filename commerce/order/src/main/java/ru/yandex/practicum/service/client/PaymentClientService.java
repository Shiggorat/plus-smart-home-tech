package ru.yandex.practicum.service.client;

import ru.yandex.practicum.commerce.dto.OrderDto;
import ru.yandex.practicum.commerce.dto.PaymentDto;

import java.math.BigDecimal;

public interface PaymentClientService {
    PaymentDto fetchCreatePayment(OrderDto order);

    BigDecimal fetchGetTotalCost(OrderDto order);

    BigDecimal fetchGetProductCost(OrderDto order);
}
