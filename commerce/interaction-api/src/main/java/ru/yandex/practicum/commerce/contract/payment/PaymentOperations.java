package ru.yandex.practicum.commerce.contract.payment;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.commerce.dto.OrderDto;
import ru.yandex.practicum.commerce.dto.PaymentDto;

import java.math.BigDecimal;

public interface PaymentOperations {
    @PostMapping
    PaymentDto createPayment(@RequestBody OrderDto order);

    @PostMapping("/totalCost")
    BigDecimal getTotalCost(@RequestBody OrderDto order);

    @PostMapping("/refund")
    void successfulPayment(@RequestBody String paymentId);

    @PostMapping("/productCost")
    BigDecimal getProductCost(@RequestBody OrderDto order);

    @PostMapping("/failed")
    void failedPayment(@RequestBody String paymentId);
}
