package ru.yandex.practicum.commerce.cart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.cart.mapper.CartMapper;
import ru.yandex.practicum.commerce.cart.model.CartProduct;
import ru.yandex.practicum.commerce.cart.model.CartState;
import ru.yandex.practicum.commerce.cart.model.ShoppingCartEntity;
import ru.yandex.practicum.commerce.cart.repository.CartProductRepository;
import ru.yandex.practicum.commerce.cart.repository.CartRepository;
import ru.yandex.practicum.commerce.cart.service.client.WarehouseClientCartService;
import ru.yandex.practicum.commerce.dto.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.commerce.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.request.ChangeProductQuantityRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartProductRepository productRepository;
    private final WarehouseClientCartService warehouseClientService;


    @Transactional
    @Override
    public ShoppingCartDto addProductsToCart(Map<String, Integer> products, String username) {
        checkUsername(username);
        Optional<ShoppingCartEntity> maybeShoppingCart = getActualCart(username);
        ShoppingCartEntity shoppingCart;
        shoppingCart = maybeShoppingCart.orElseGet(() ->
                createShoppingCart(username));
        checkProductsQuantity(shoppingCart, products);
        saveProducts(shoppingCart, products);
        return getActualCart(username).map(cartMapper::toDto)
                .orElse(null);
    }

    @Override
    public ShoppingCartDto getCart(String username) {
        checkUsername(username);
        return getActualCart(username)
                .map(cart -> {
                    ShoppingCartDto dto = cartMapper.toDto(cart);
                    dto.setProducts(cartMapper.toDtoMap(cart.getProducts()));
                    return dto;
                })
                .orElse(null);
    }

    @Override
    public void deactivateShoppingCart(String username) {
        checkUsername(username);
        Optional<ShoppingCartEntity> maybeCart = getActualCart(username);
        if (maybeCart.isPresent()) {
            ShoppingCartEntity cart = maybeCart.get();
            cart.setCartState(CartState.DEACTIVATE);
            cartRepository.save(cart);
        }
    }

    @Transactional
    @Override
    public ShoppingCartDto removeProductsFromCart(String username, Set<String> productIds) {
        checkUsername(username);
        Optional<ShoppingCartEntity> maybeCart = getActualCart(username);
        if (maybeCart.isPresent()) {
            ShoppingCartEntity shoppingCart = maybeCart.get();
            List<CartProduct> products = shoppingCart.getProducts();
            Set<String> productIdsInCart = products.stream().map(CartProduct::getProductId).collect(Collectors.toSet());
            if (!productIdsInCart.containsAll(productIds)) {
                throw new NoProductsInShoppingCartException("The items you are looking for are not in the shopping cart.");
            }
            productRepository.removeByProductIdIn(productIds);
            return getActualCart(username).map(cartMapper::toDto)
                    .orElse(null);
        }
        return null;
    }

    @Override
    public ShoppingCartDto changeQuantity(String username, ChangeProductQuantityRequest request) {
        checkUsername(username);
        CartProduct cartProduct = productRepository.findByProductId(request.getProductId()).orElseThrow(() ->
                new NoProductsInShoppingCartException("The items you are looking for are not in the shopping cart."));
        productRepository.save(cartProduct);
        Optional<ShoppingCartEntity> shoppingCart = getActualCart(username);
        return shoppingCart.map(cartMapper::toDto).orElse(null);
    }

    private void checkUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new NotAuthorizedUserException("The user is not logged in");
        }
    }

    private Optional<ShoppingCartEntity> getActualCart(String username) {
        return cartRepository.findByUsernameAndCartState(username, CartState.ACTIVE);
    }

    private ShoppingCartEntity createShoppingCart(String username) {
        ShoppingCartEntity shoppingCart = new ShoppingCartEntity();
        shoppingCart.setCartId(UUID.randomUUID().toString());
        shoppingCart.setUsername(username);
        shoppingCart.setProducts(new ArrayList<>());
        return shoppingCart;
    }

    private void saveProducts(ShoppingCartEntity shoppingCart, Map<String, Integer> products) {
        List<CartProduct> cartProducts = cartMapper.toEntityList(products);
        shoppingCart.getProducts().addAll(cartProducts);
        cartRepository.save(shoppingCart);
    }

    private BookedProductsDto checkProductsQuantity(ShoppingCartEntity shoppingCartEntity, Map<String, Integer> products) {
        ShoppingCartDto dto = cartMapper.toDto(shoppingCartEntity);
        dto.setProducts(products);
        return warehouseClientService.fetchCheckProductsQuantity(dto);
    }
}
