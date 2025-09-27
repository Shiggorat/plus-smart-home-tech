package ru.yandex.practicum.service.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.client.WarehouseClient;
import ru.yandex.practicum.commerce.request.ShippedToDeliveryRequest;
import ru.yandex.practicum.service.client.WarehouseClientDeliveryService;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseClientDeliveryService {

    private final WarehouseClient client;

    @Override
    public void fetchShippedToDelivery(ShippedToDeliveryRequest request) {
        client.shipProducts(request);
    }

}
