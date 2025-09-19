package ru.yandex.practicum.commerce.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import ru.yandex.practicum.commerce.enums.ProductCategory;

@Data
@AllArgsConstructor
public class GetProductsParams {
    private ProductCategory productCategory;
    private Pageable pageable;
}
