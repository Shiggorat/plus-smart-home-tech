package ru.yandex.practicum.service.client;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ShoppingStoreClientPaymentService {
    Map<String, BigDecimal> fetchGetProductsPrice(List<String> productsIds);
}
