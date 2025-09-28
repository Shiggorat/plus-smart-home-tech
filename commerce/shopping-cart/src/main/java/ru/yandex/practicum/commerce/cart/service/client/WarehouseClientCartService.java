package ru.yandex.practicum.commerce.cart.service.client;

import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;

public interface WarehouseClientCartService {
    BookedProductsDto fetchCheckProductsQuantity(ShoppingCartDto cartDto);
}
