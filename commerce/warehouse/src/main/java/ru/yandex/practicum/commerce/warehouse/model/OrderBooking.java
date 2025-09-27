package ru.yandex.practicum.commerce.warehouse.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Entity
@Getter
@Setter
@ToString
@Table(name = "order_booking", schema = "commerce_warehouse")
public class OrderBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "delivery_id")
    private String deliveryId;
    @ElementCollection
    @CollectionTable(
            name = "order_booking_products",
            schema = "commerce_warehouse",
            joinColumns = @JoinColumn(name = "order_booking_id")
    )
    @MapKeyColumn(name = "product_code")
    @Column(name = "quantity")
    private Map<String, Integer> products;
}
