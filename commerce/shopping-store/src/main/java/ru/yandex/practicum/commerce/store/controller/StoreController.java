package ru.yandex.practicum.commerce.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.contract.shopping.store.ShoppingStoreOperations;
import ru.yandex.practicum.commerce.dto.ProductDto;
import ru.yandex.practicum.commerce.dto.ProductPageDto;
import ru.yandex.practicum.commerce.enums.ProductCategory;
import ru.yandex.practicum.commerce.enums.QuantityState;
import ru.yandex.practicum.commerce.logging.Logging;
import ru.yandex.practicum.commerce.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.commerce.store.dto.GetProductsParams;
import ru.yandex.practicum.commerce.store.service.StoreService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-store")
@Slf4j
public class StoreController implements ShoppingStoreOperations {

    private final StoreService service;

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Logging
    @Override
    public ProductDto addProduct(@RequestBody @Valid ProductDto product) {
        return service.addProduct(product);
    }

    @PostMapping
    @Logging
    @Override
    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto) {
        return service.updateProduct(productDto);
    }

    @PostMapping("/removeProductFromStore")
    @Logging
    @Override
    public boolean removeProduct(@RequestBody String id) {
        return service.deactivateProduct(id);
    }

    @PostMapping("/quantityState")
    @Logging
    @Override
    public boolean setQuantityState(@RequestParam QuantityState quantityState, @RequestParam String productId) {
        SetProductQuantityStateRequest request = new SetProductQuantityStateRequest(productId, quantityState);
        return service.setQuantityState(request);
    }

    @GetMapping("/{productId}")
    @Logging
    @Override
    public ProductDto getProductById(@PathVariable String productId) {
        return service.getProductById(productId);
    }

    @GetMapping
    @Logging
    @Override
    public ProductPageDto getProducts(@RequestParam ProductCategory category, @PageableDefault(sort = "productName") Pageable pageable) {
        GetProductsParams params = new GetProductsParams(category, pageable);
        return service.getProducts(params);
    }
}
