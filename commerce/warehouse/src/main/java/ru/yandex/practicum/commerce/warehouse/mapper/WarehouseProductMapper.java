package ru.yandex.practicum.commerce.warehouse.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.commerce.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.commerce.warehouse.model.WarehouseProduct;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WarehouseProductMapper {
    WarehouseProduct toEntity(NewProductInWarehouseRequest request);
}
