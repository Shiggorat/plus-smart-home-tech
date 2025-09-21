package ru.yandex.practicum.commerce.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.commerce.warehouse.model.WarehouseProduct;

import java.util.List;
import java.util.Set;


public interface ProductRepository extends JpaRepository<WarehouseProduct, String> {
    boolean existsByProductId(String id);

    List<WarehouseProduct> findByProductIdIn(Set<String> productIds);
}
