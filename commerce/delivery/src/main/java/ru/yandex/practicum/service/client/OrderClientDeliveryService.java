package ru.yandex.practicum.service.client;

public interface OrderClientDeliveryService {
    void failedDelivery(String orderId);

    void successfulDelivery(String orderId);
}
