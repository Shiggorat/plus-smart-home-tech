package ru.yandex.practicum.commerce.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.commerce.client.config.FeignClientConfig;
import ru.yandex.practicum.commerce.contract.warehouse.WarehouseOperations;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse", configuration = FeignClientConfig.class)
public interface WarehouseClient extends WarehouseOperations {
}
