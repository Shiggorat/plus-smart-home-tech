package ru.yandex.practicum.commerce.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.commerce.client.config.FeignClientConfig;
import ru.yandex.practicum.commerce.contract.shopping.store.ShoppingStoreOperations;

@FeignClient(name = "shopping-store", path = "/api/v1/shopping-store", configuration = FeignClientConfig.class)
public interface ShoppingStoreClient extends ShoppingStoreOperations {
}
