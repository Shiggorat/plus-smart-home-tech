package ru.yandex.practicum.service.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.client.WarehouseClient;
import ru.yandex.practicum.commerce.dto.AddressDto;
import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.request.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.service.client.WarehouseClientOrderService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseClientOrderService {

    private final WarehouseClient warehouseClient;

    @Override
    public void fetchReturnBookedProducts(Map<String, Integer> products) {
        warehouseClient.returnBookedProducts(products);
    }

    @Override
    public AddressDto getWarehouseAddress() {
        return warehouseClient.getAddress();
    }

    @Override
    public BookedProductsDto fetchCheckProductsQuantity(ShoppingCartDto cartDto) {
        return warehouseClient.checkProductsQuantity(cartDto);
    }

    @Override
    public void assemblyProducts(AssemblyProductsForOrderRequest request) {
        warehouseClient.assemblyProducts(request);
    }


}
