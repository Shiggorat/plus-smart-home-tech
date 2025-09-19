package ru.yandex.practicum.commerce.warehouse.service;

import ru.yandex.practicum.commerce.dto.AddressDto;
import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.request.AddProductUnitRequest;
import ru.yandex.practicum.commerce.request.NewProductInWarehouseRequest;

public interface WarehouseService {
    void createProduct(NewProductInWarehouseRequest request);

    BookedProductsDto checkProductsQuantity(ShoppingCartDto cart);

    void addProductQuantity(AddProductUnitRequest request);

    AddressDto getAddress();
}
