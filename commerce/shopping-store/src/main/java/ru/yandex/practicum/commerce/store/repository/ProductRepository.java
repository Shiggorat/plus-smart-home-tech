package ru.yandex.practicum.commerce.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.commerce.enums.ProductCategory;
import ru.yandex.practicum.commerce.enums.QuantityState;
import ru.yandex.practicum.commerce.store.model.ProductEntity;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    boolean existsByProductId(String id);

    Optional<ProductEntity> findByProductId(String id);

    @Transactional
    @Modifying
    @Query("UPDATE ProductEntity p SET p.quantityState = :quantityState WHERE p.productId = :productId")
    int setProductQuantityState(QuantityState quantityState, String productId);

    @Transactional
    @Modifying
    @Query("UPDATE ProductEntity p SET p.productState = 'DEACTIVATE' WHERE p.productId = :id")
    int deactivateProduct(String id);

    Page<ProductEntity> findAllByProductCategory(ProductCategory productCategory, Pageable pageable);
}
