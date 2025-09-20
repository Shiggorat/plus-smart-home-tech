package ru.yandex.practicum.commerce.store.service;

import ru.yandex.practicum.commerce.dto.ProductDto;
import ru.yandex.practicum.commerce.dto.ProductPageDto;
import ru.yandex.practicum.commerce.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.commerce.store.dto.GetProductsParams;


public interface StoreService {
    ProductDto addProduct(ProductDto productDto);

    ProductDto updateProduct(ProductDto productDto);

    boolean deactivateProduct(String id);

    boolean setQuantityState(SetProductQuantityStateRequest request);

    ProductDto getProductById(String id);

    ProductPageDto getProducts(GetProductsParams getProductsParams);
}
