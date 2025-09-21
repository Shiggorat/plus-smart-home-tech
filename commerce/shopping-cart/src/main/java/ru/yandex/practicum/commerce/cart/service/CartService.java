package ru.yandex.practicum.commerce.cart.service;

import ru.yandex.practicum.commerce.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.request.ChangeProductQuantityRequest;

import java.util.Map;
import java.util.Set;

public interface CartService {

    ShoppingCartDto addProductsToCart(Map<String, Integer> product, String username);

    ShoppingCartDto getCart(String username);

    void deactivateShoppingCart(String username);

    ShoppingCartDto removeProductsFromCart(String username, Set<String> productIds);

    ShoppingCartDto changeQuantity(String username, ChangeProductQuantityRequest request);
}
