package ru.yandex.practicum.commerce.warehouse.service;

import ru.yandex.practicum.commerce.dto.AddressDto;
import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.request.AddProductUnitRequest;
import ru.yandex.practicum.commerce.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.commerce.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.commerce.request.ShippedToDeliveryRequest;

import java.util.Map;

public interface WarehouseService {
    void createProduct(NewProductInWarehouseRequest request);

    BookedProductsDto checkProductsQuantity(ShoppingCartDto cart);

    void addProductQuantity(AddProductUnitRequest request);

    AddressDto getAddress();

    void returnBookedProducts(Map<String, Integer> returnedProducts);

    BookedProductsDto assemblyProducts(AssemblyProductsForOrderRequest request);

    void shipProducts(ShippedToDeliveryRequest request);
}
