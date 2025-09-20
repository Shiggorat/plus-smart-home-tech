package ru.yandex.practicum.commerce.contract.shopping.store;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.dto.ProductDto;
import ru.yandex.practicum.commerce.dto.ProductPageDto;
import ru.yandex.practicum.commerce.enums.ProductCategory;
import ru.yandex.practicum.commerce.enums.QuantityState;

public interface ShoppingStoreOperations {
    @PutMapping
    public ProductDto addProduct(@RequestBody @Valid ProductDto product);

    @PostMapping
    public ProductDto updateProduct(@RequestBody @Valid ProductDto productDto);

    @PostMapping("/removeProductFromStore")
    public boolean removeProduct(@RequestBody String id);

    @PostMapping("/quantityState")
    public boolean setQuantityState(@RequestParam QuantityState quantityState, @RequestParam String productId);

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable String productId);

    @GetMapping
    public ProductPageDto getProducts(@RequestParam ProductCategory category,
                                      @PageableDefault(sort = "productName") Pageable pageable);
}
