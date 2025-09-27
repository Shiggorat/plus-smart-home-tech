package ru.yandex.practicum.commerce.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.commerce.client.config.FeignClientConfig;
import ru.yandex.practicum.commerce.contract.delivery.DeliveryOperations;

@FeignClient(name = "delivery", path = "/api/v1/delivery", configuration = FeignClientConfig.class)
public interface DeliveryClient extends DeliveryOperations {
}
