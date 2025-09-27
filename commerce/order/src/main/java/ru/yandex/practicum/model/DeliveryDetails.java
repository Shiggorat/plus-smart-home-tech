package ru.yandex.practicum.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.math.BigDecimal;

@Embeddable
@Data
public class DeliveryDetails {
    @Column(name = "delivery_id")
    private String deliveryId;
    @Column(name = "delivery_weight")
    private double deliveryWeight;
    @Column(name = "delivery_volume")
    private double deliveryVolume;
    @Column(name = "delivery_fragile")
    private boolean fragile;
    @Column(name = "delivery_price")
    private BigDecimal deliveryPrice;
}
