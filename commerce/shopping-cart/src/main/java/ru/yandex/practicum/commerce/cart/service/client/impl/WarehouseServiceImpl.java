package ru.yandex.practicum.commerce.cart.service.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.cart.service.client.WarehouseClientCartService;
import ru.yandex.practicum.commerce.client.WarehouseClient;
import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseClientCartService {

    private final WarehouseClient warehouseClient;

    @Override
    public BookedProductsDto fetchCheckProductsQuantity(ShoppingCartDto cartDto) {
        return warehouseClient.checkProductsQuantity(cartDto);
    }
}
