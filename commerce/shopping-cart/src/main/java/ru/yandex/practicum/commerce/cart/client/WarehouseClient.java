package ru.yandex.practicum.commerce.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.commerce.contract.warehouse.WarehouseOperations;

@FeignClient(name = "warehouse", path = "/api/v1/warehouse")
public interface WarehouseClient extends WarehouseOperations {
}
