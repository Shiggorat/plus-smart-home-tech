package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "payment", schema = "commerce_payment")
@Getter
@Setter
@ToString
public class PaymentEntity {
    @Column(name = "payment_id")
    @Id
    private String paymentId;
    @Column(name = "product_price")
    private BigDecimal productPrice;
    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_state")
    private PaymentState paymentState;
    @Column(name = "order_id")
    private String orderId;
}
