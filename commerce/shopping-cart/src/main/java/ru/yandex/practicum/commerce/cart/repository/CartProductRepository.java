package ru.yandex.practicum.commerce.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.cart.model.CartProduct;

import java.util.Optional;
import java.util.Set;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {
    Optional<CartProduct> findByProductId(String productId);

    @Transactional
    @Modifying
    void removeByProductIdIn(Set<String> productIds);
}
