package ru.yandex.practicum.commerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.commerce.enums.ProductCategory;
import ru.yandex.practicum.commerce.enums.ProductState;
import ru.yandex.practicum.commerce.enums.QuantityState;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private String productId;
    @NotNull
    private String productName;
    @NotNull
    private String description;
    @NotNull
    private String imageSrc;
    @NotNull
    private QuantityState quantityState;
    @NotNull
    private ProductState productState;
    @NotNull
    private ProductCategory productCategory;
    @NotNull
    private BigDecimal price;
}
