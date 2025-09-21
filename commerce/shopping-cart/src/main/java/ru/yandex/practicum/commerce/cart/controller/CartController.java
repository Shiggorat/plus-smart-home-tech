package ru.yandex.practicum.commerce.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.cart.service.CartService;
import ru.yandex.practicum.commerce.contract.shopping.cart.ShoppingCartOperations;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.logging.Logging;
import ru.yandex.practicum.commerce.request.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
public class CartController implements ShoppingCartOperations {

    private final CartService cartService;

    @GetMapping
    @Logging
    @Override
    public ShoppingCartDto getShoppingCart(@RequestParam String username) {
        return cartService.getCart(username);
    }

    @PutMapping
    @Logging
    @Override
    public ShoppingCartDto addProductsToCart(@RequestParam String username, @RequestBody Map<String, Integer> products) {
        return cartService.addProductsToCart(products, username);
    }

    @DeleteMapping
    @Logging
    @Override
    public void deactivateShoppingCart(@RequestParam String username) {
        cartService.deactivateShoppingCart(username);
    }

    @PostMapping("/remove")
    @Logging
    @Override
    public ShoppingCartDto removeProductsFromCart(@RequestParam String username, @RequestBody Set<String> productIds) {
        return cartService.removeProductsFromCart(username, productIds);
    }

    @PostMapping("/change-quantity")
    @Logging
    @Override
    public ShoppingCartDto changeQuantity(@RequestParam String username, @RequestBody ChangeProductQuantityRequest request) {
        return cartService.changeQuantity(username, request);
    }

}
