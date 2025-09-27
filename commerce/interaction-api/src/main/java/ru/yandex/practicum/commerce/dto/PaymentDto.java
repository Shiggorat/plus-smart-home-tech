package ru.yandex.practicum.commerce.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {
    private String paymentId;
    private BigDecimal totalPayment;
    private BigDecimal deliveryTotal;
    private BigDecimal productTotal;
    private BigDecimal feeTotal;
}
