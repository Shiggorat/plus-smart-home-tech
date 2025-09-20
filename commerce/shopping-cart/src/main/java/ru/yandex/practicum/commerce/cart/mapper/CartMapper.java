package ru.yandex.practicum.commerce.cart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.commerce.cart.model.CartProduct;
import ru.yandex.practicum.commerce.cart.model.ShoppingCartEntity;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartMapper {

    default ShoppingCartDto toDto(ShoppingCartEntity shoppingCart) {
        ShoppingCartDto dto = new ShoppingCartDto();
        dto.setCartId(shoppingCart.getCartId());
        dto.setProducts(toDtoMap(shoppingCart.getProducts()));
        return dto;
    }

    default ShoppingCartEntity toEntity(ShoppingCartDto shoppingCartDto, String username) {
        if (shoppingCartDto == null) {
            return null;
        }
        ShoppingCartEntity entity = new ShoppingCartEntity();
        entity.setProducts(new ArrayList<>());
        entity.setUsername(username);
        if (shoppingCartDto.getCartId() != null) {
            entity.setCartId(shoppingCartDto.getCartId());
        }
        return entity;
    }

    default Map<String, Integer> toDtoMap(List<CartProduct> cartProducts) {
        Map<String, Integer> products = new HashMap<>();
        for (CartProduct cartProduct : cartProducts) {
            products.put(cartProduct.getProductId(), cartProduct.getQuantity());
        }
        return products;
    }

    default List<CartProduct> toEntityList(Map<String, Integer> products) {
        List<CartProduct> cartProducts = new ArrayList<>();
        for (Map.Entry<String, Integer> product : products.entrySet()) {
            CartProduct cartProduct = new CartProduct();
            cartProduct.setProductId(product.getKey());
            cartProduct.setQuantity(product.getValue());
            cartProducts.add(cartProduct);
        }
        return cartProducts;
    }
}
