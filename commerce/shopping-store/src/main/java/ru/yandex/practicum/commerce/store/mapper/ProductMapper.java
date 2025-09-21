package ru.yandex.practicum.commerce.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.Page;
import ru.yandex.practicum.commerce.dto.ProductDto;
import ru.yandex.practicum.commerce.dto.ProductPageDto;
import ru.yandex.practicum.commerce.store.model.ProductEntity;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    ProductDto toDto(ProductEntity entity);

    ProductEntity toEntity(ProductDto dto);

    default ProductPageDto toProductPageDto(Page<ProductEntity> products) {
        List<ProductDto> content = products.getContent().stream()
                .map(this::toDto)
                .toList();

        List<ProductPageDto.SortDto> sortList = products.getSort().stream()
                .map(order -> new ProductPageDto.SortDto(order.getProperty(), order.getDirection().name()))
                .toList();

        ProductPageDto pageDto = new ProductPageDto();
        pageDto.setContent(content);
        pageDto.setSort(sortList);
        return pageDto;
    }
}