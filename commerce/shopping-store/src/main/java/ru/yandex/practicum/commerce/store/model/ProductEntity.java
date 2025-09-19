package ru.yandex.practicum.commerce.store.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.yandex.practicum.commerce.enums.ProductCategory;
import ru.yandex.practicum.commerce.enums.ProductState;
import ru.yandex.practicum.commerce.enums.QuantityState;

import java.math.BigDecimal;

@Entity
@Table(name = "products", schema = "commerce_store")
@Data
public class ProductEntity {

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "image_src")
    private String imageSrc;

    @Enumerated(EnumType.STRING)
    @Column(name = "quantity_state")
    private QuantityState quantityState;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_state")
    private ProductState productState;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_category")
    private ProductCategory productCategory;

    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
