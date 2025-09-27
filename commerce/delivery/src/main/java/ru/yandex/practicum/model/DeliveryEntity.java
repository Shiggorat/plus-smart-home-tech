package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.commerce.enums.DeliveryState;

@Entity
@Table(name = "delivery", schema = "commerce_delivery")
@Getter
@Setter
@ToString
public class DeliveryEntity {
    @Id
    @Column(name = "delivery_id")
    private String deliveryId;
    @Column(name = "delivery_volume")
    private double deliveryVolume;
    @Column(name = "delivery_weight")
    private double deliveryWeight;
    private boolean fragile;
    @JoinColumn(name = "address_from_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address fromAddress;
    @JoinColumn(name = "address_to_id", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    private Address toAddress;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "delivery_state")
    private DeliveryState deliveryState = DeliveryState.CREATED;
}
