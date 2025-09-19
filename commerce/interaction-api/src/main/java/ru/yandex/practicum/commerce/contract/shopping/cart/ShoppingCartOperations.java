package ru.yandex.practicum.commerce.contract.shopping.cart;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.request.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;

public interface ShoppingCartOperations {
    @GetMapping
    public ShoppingCartDto getShoppingCart(@RequestParam String username);

    @PutMapping
    public ShoppingCartDto addProductsToCart(@RequestParam String username, @RequestBody Map<String, Integer> productsMap);

    @DeleteMapping
    public void deactivateShoppingCart(@RequestParam String username);

    @PostMapping("/remove")
    public ShoppingCartDto removeProductsFromCart(@RequestParam String username, @RequestBody Set<String> productIds);

    @PostMapping("/change-quantity")
    public ShoppingCartDto changeQuantity(@RequestParam String username, @RequestBody ChangeProductQuantityRequest request);
}
