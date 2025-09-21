package ru.yandex.practicum.commerce.cart.service;

import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;

public interface WarehouseService {
    BookedProductsDto fetchCheckProductsQuantity(ShoppingCartDto cartDto);
}
