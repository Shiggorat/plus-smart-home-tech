package ru.yandex.practicum.commerce.warehouse.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "products", schema = "commerce_warehouse")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class WarehouseProduct {
    @Id
    @Column(name = "product_id")
    private String productId;
    private boolean fragile;
    @Embedded
    private Dimension dimension;
    private float weight;
    private int quantity;
}
