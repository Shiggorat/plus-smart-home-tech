package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.commerce.dto.AddressDto;
import ru.yandex.practicum.commerce.dto.DeliveryDto;
import ru.yandex.practicum.commerce.request.CreateNewDeliveryRequest;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.DeliveryEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeliveryMapper {
    default DeliveryEntity toEntity(CreateNewDeliveryRequest request) {
        DeliveryEntity delivery = new DeliveryEntity();
        delivery.setFromAddress(toEntity(request.getFromAddress()));
        delivery.setToAddress(toEntity(request.getToAddress()));
        delivery.setDeliveryWeight(request.getOrderDto().getDeliveryWeight());
        delivery.setDeliveryVolume(request.getOrderDto().getDeliveryVolume());
        delivery.setFragile(request.getOrderDto().getFragile());
        return delivery;
    }

    DeliveryDto toDto(DeliveryEntity entity);

    AddressDto toDto(Address address);

    Address toEntity(AddressDto addressDto);
}
