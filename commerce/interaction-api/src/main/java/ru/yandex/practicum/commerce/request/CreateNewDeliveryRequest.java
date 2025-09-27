package ru.yandex.practicum.commerce.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.commerce.dto.AddressDto;
import ru.yandex.practicum.commerce.dto.OrderDto;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateNewDeliveryRequest {
    OrderDto orderDto;
    AddressDto fromAddress;
    AddressDto toAddress;
}
