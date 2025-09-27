package ru.yandex.practicum.commerce.dto;

import lombok.Data;
import ru.yandex.practicum.commerce.enums.OrderState;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Data
public class OrderDto {
    private String orderId;
    private String shoppingCartId;
    private Map<String, Integer> products = new HashMap<>();
    private String paymentId;
    private String deliveryId;
    private OrderState orderState;
    private Float deliveryWeight;
    private Float deliveryVolume;
    private Boolean fragile;
    private BigDecimal totalPrice;
    private BigDecimal deliveryPrice;
    private BigDecimal productPrice;
}

