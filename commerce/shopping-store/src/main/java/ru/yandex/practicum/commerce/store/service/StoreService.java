package ru.yandex.practicum.commerce.store.service;

import ru.yandex.practicum.commerce.dto.ProductDto;
import ru.yandex.practicum.commerce.dto.ProductPageDto;
import ru.yandex.practicum.commerce.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.commerce.store.dto.GetProductsParams;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface StoreService {
    ProductDto addProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    boolean deactivateProduct(String id);

    boolean setQuantityState(SetProductQuantityStateRequest request);

    ProductDto getProductById(String id);

    ProductPageDto getProducts(GetProductsParams getProductsParams);

    Map<String, BigDecimal> getProductsPrice(List<String> productsIds);
}
