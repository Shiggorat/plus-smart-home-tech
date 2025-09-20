package ru.yandex.practicum.commerce.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.commerce.dto.DimensionDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewProductInWarehouseRequest {
    @NotBlank
    private String productId;
    private Boolean fragile;
    @NotNull
    @Valid
    private DimensionDto dimension;
    @NotNull
    private double weight;
}
