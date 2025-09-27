package ru.yandex.practicum.commerce.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ProductReturnRequest {
    @NotBlank
    private String orderId;
    @NotEmpty
    private Map<String, Integer> products = new HashMap<>();
}
