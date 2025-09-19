package ru.yandex.practicum.commerce.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.commerce.enums.QuantityState;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetProductQuantityStateRequest {
    @NotBlank
    private String productId;
    @NotNull
    private QuantityState quantityState;
}
