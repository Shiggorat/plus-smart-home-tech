package ru.yandex.practicum.commerce.warehouse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.contract.warehouse.WarehouseOperations;
import ru.yandex.practicum.commerce.dto.AddressDto;
import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.exception.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.commerce.logging.Logging;
import ru.yandex.practicum.commerce.request.AddProductUnitRequest;
import ru.yandex.practicum.commerce.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.commerce.warehouse.service.WarehouseService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/warehouse")
@Slf4j
public class WarehouseController implements WarehouseOperations {

    private final WarehouseService service;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Logging
    @Override
    public void createProduct(@RequestBody @Valid NewProductInWarehouseRequest request) {
        service.createProduct(request);
    }

    @PostMapping("/check")
    @Logging
    @Override
    public BookedProductsDto checkProductsQuantity(@RequestBody ShoppingCartDto cart)
            throws ProductInShoppingCartLowQuantityInWarehouseException {
        return service.checkProductsQuantity(cart);
    }


    @PostMapping("/add")
    @Logging
    @Override
    public void addProductQuantity(@RequestBody AddProductUnitRequest request) {
        service.addProductQuantity(request);
    }

    @GetMapping("/address")
    @Logging
    @Override
    public AddressDto getAddress() {
        return service.getAddress();
    }
}

