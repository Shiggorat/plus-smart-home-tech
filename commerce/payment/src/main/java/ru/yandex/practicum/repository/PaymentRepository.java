package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
}
