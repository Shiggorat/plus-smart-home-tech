package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.commerce.enums.OrderState;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "orders", schema = "commerce_order")
@Getter
@Setter
@ToString
public class OrderEntity {
    @Id
    @Column(name = "order_id")
    private String orderId;
    private String username;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private OrderState orderState = OrderState.NEW;
    @Column(name = "shopping_cart_id")
    private String shoppingCartId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private List<OrderProduct> products;
    @Column(name = "payment_id")
    private String paymentId;
    @Embedded
    private DeliveryDetails deliveryDetails;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_address_id", referencedColumnName = "id")
    private Address deliveryAddress;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "product_price")
    private BigDecimal productPrice;
}
