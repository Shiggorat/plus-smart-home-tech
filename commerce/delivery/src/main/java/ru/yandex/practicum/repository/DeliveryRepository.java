package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.DeliveryEntity;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<DeliveryEntity, String> {
    Optional<DeliveryEntity> findByOrderId(String orderId);
}
