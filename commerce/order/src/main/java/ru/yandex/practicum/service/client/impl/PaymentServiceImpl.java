package ru.yandex.practicum.service.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.client.PaymentClient;
import ru.yandex.practicum.commerce.dto.OrderDto;
import ru.yandex.practicum.commerce.dto.PaymentDto;
import ru.yandex.practicum.service.client.PaymentClientService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentClientService {

    private final PaymentClient paymentClient;

    @Override
    public PaymentDto fetchCreatePayment(OrderDto order) {
        return paymentClient.createPayment(order);
    }

    @Override
    public BigDecimal fetchGetTotalCost(OrderDto order) {
        return paymentClient.getTotalCost(order);
    }

    @Override
    public BigDecimal fetchGetProductCost(OrderDto order) {
        return paymentClient.getProductCost(order);
    }

}
