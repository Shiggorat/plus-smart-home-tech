package ru.yandex.practicum.commerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.yandex.practicum.commerce.enums.DeliveryState;

@Data
public class DeliveryDto {
    private String deliveryId;
    @NotNull
    private AddressDto fromAddress;
    @NotNull
    private AddressDto toAddress;
    @NotBlank
    private String orderId;
    private DeliveryState deliveryState;
}
