package ru.yandex.practicum.commerce.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.commerce.client.config.FeignClientConfig;
import ru.yandex.practicum.commerce.contract.order.OrderOperations;

@FeignClient(name = "order", path = "/api/v1/order", configuration = FeignClientConfig.class)
public interface OrderClient extends OrderOperations {
}
