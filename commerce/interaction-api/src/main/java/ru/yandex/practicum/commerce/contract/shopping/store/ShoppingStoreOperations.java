package ru.yandex.practicum.commerce.contract.shopping.store;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.dto.ProductDto;
import ru.yandex.practicum.commerce.dto.ProductPageDto;
import ru.yandex.practicum.commerce.enums.ProductCategory;
import ru.yandex.practicum.commerce.enums.QuantityState;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ShoppingStoreOperations {
    @PutMapping
    ProductDto addProduct(@RequestBody @Valid ProductDto product);

    @PostMapping
    ProductDto updateProduct(@RequestBody @Valid ProductDto productDto);

    @PostMapping("/removeProductFromStore")
    boolean removeProduct(@RequestBody String id);

    @PostMapping("/quantityState")
    boolean setQuantityState(@RequestParam QuantityState quantityState, @RequestParam String productId);

    @GetMapping("/{productId}")
    ProductDto getProductById(@PathVariable String productId);

    @GetMapping
    ProductPageDto getProducts(@RequestParam ProductCategory category,
                               @PageableDefault(sort = "productName") Pageable pageable);

    @GetMapping("/price")
    Map<String, BigDecimal> getProductsPrice(@RequestBody List<String> productsIds);
}
