package ru.yandex.practicum.commerce.cart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.cart.client.WarehouseClient;
import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseClient warehouseClient;

    @Override
    public BookedProductsDto fetchCheckProductsQuantity(ShoppingCartDto cartDto) {
        return warehouseClient.checkProductsQuantity(cartDto);
    }
}
