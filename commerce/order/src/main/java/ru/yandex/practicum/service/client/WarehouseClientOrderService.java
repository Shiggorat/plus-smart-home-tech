package ru.yandex.practicum.service.client;

import ru.yandex.practicum.commerce.dto.AddressDto;
import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.request.AssemblyProductsForOrderRequest;

import java.util.Map;

public interface WarehouseClientOrderService {
    void fetchReturnBookedProducts(Map<String, Integer> products);

    AddressDto getWarehouseAddress();

    BookedProductsDto fetchCheckProductsQuantity(ShoppingCartDto cartDto);

    void assemblyProducts(AssemblyProductsForOrderRequest request);
}
