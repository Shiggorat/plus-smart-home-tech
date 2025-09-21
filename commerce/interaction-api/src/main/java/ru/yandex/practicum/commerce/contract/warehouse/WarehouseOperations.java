package ru.yandex.practicum.commerce.contract.warehouse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.commerce.dto.AddressDto;
import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.exception.ProductInShoppingCartLowQuantityInWarehouseException;
import ru.yandex.practicum.commerce.request.AddProductUnitRequest;
import ru.yandex.practicum.commerce.request.NewProductInWarehouseRequest;

public interface WarehouseOperations {
    @PutMapping
    void createProduct(@RequestBody @Valid NewProductInWarehouseRequest request);

    @PostMapping("/check")
    BookedProductsDto checkProductsQuantity(@RequestBody ShoppingCartDto cart) throws ProductInShoppingCartLowQuantityInWarehouseException;

    @PostMapping("/add")
    void addProductQuantity(@RequestBody AddProductUnitRequest request);

    @GetMapping("/address")
    AddressDto getAddress();
}
