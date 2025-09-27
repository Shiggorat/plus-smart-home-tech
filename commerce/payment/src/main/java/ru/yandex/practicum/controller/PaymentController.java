package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.commerce.contract.payment.PaymentOperations;
import ru.yandex.practicum.commerce.dto.OrderDto;
import ru.yandex.practicum.commerce.dto.PaymentDto;
import ru.yandex.practicum.service.PaymentService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController implements PaymentOperations {
    private final PaymentService paymentService;

    @Override
    @PostMapping
    public PaymentDto createPayment(@RequestBody OrderDto order) {
        return paymentService.createPayment(order);
    }

    @Override
    @PostMapping("/totalCost")
    public BigDecimal getTotalCost(@RequestBody OrderDto order) {
        return paymentService.getTotalCost(order);
    }

    @Override
    @PostMapping("/refund")
    public void successfulPayment(@RequestBody String paymentId) {
        paymentService.successfulPayment(paymentId);
    }

    @Override
    @PostMapping("/productCost")
    public BigDecimal getProductCost(@RequestBody OrderDto order) {
        return paymentService.getProductCost(order);
    }

    @Override
    @PostMapping("/failed")
    public void failedPayment(@RequestBody String paymentId) {
        paymentService.failedPayment(paymentId);
    }

}
