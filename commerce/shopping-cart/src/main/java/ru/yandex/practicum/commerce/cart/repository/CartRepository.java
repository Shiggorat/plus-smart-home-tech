package ru.yandex.practicum.commerce.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.commerce.cart.model.CartState;
import ru.yandex.practicum.commerce.cart.model.ShoppingCartEntity;

import java.util.Optional;

public interface CartRepository extends JpaRepository<ShoppingCartEntity, String> {
    Optional<ShoppingCartEntity> findByUsernameAndCartState(String username, CartState cartState);
}
