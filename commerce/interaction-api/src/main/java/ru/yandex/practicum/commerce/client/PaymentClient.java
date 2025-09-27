package ru.yandex.practicum.commerce.client;

import org.springframework.cloud.openfeign.FeignClient;
import ru.yandex.practicum.commerce.client.config.FeignClientConfig;
import ru.yandex.practicum.commerce.contract.payment.PaymentOperations;

@FeignClient(name = "payment", path = "/api/v1/payment", configuration = FeignClientConfig.class)
public interface PaymentClient extends PaymentOperations {
}
