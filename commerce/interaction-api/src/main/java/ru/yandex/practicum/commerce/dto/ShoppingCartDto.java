package ru.yandex.practicum.commerce.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ShoppingCartDto {
    private String cartId;
    private Map<String, Integer> products = new HashMap<>();
}
