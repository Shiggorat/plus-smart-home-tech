package ru.yandex.practicum.commerce.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddProductUnitRequest {
    @NotBlank
    private String productId;
    @NotNull
    @Min(value = 1, message = "You can add at least 1 item")
    private Integer quantity;
}