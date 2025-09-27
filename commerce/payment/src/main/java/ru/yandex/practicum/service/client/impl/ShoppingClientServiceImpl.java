package ru.yandex.practicum.service.client.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.client.ShoppingStoreClient;
import ru.yandex.practicum.service.client.ShoppingStoreClientPaymentService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ShoppingClientServiceImpl implements ShoppingStoreClientPaymentService {

    private final ShoppingStoreClient client;

    @Override
    public Map<String, BigDecimal> fetchGetProductsPrice(List<String> productsIds) {
        return client.getProductsPrice(productsIds);
    }
}
