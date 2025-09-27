package ru.yandex.practicum.service.client;

import ru.yandex.practicum.commerce.request.ShippedToDeliveryRequest;

public interface WarehouseClientDeliveryService {
    void fetchShippedToDelivery(ShippedToDeliveryRequest request);
}
