package ru.yandex.practicum.commerce.cart.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "cart", schema = "commerce_cart")
public class ShoppingCartEntity {
    @Id
    @Column(name = "id")
    private String cartId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private List<CartProduct> products;
    @Enumerated(EnumType.STRING)
    @Column(name = "cart_state")
    private CartState cartState = CartState.ACTIVE;
    private String username;
}
