package ru.yandex.practicum.commerce.contract.delivery;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.commerce.dto.DeliveryDto;
import ru.yandex.practicum.commerce.request.CreateNewDeliveryRequest;

import java.math.BigDecimal;

public interface DeliveryOperations {
    @PutMapping
    DeliveryDto createDelivery(@RequestBody CreateNewDeliveryRequest request);

    @PostMapping("/successful")
    void successfulDelivery(@RequestBody String orderId);

    @PostMapping("/picked")
    void pickProducts(@RequestBody String orderId);

    @PostMapping("/failed")
    void failedDelivery(@RequestBody String orderId);

    @PostMapping("/cost")
    BigDecimal calculateDeliveryCost(@RequestBody String deliveryId);

}
