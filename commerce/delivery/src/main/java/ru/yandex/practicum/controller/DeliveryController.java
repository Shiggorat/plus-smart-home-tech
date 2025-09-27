package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.contract.delivery.DeliveryOperations;
import ru.yandex.practicum.commerce.dto.DeliveryDto;
import ru.yandex.practicum.commerce.request.CreateNewDeliveryRequest;
import ru.yandex.practicum.service.DeliveryService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController implements DeliveryOperations {

    private final DeliveryService deliveryService;

    @Override
    @PutMapping
    public DeliveryDto createDelivery(@RequestBody CreateNewDeliveryRequest request) {
        return deliveryService.createDelivery(request);
    }

    @Override
    @PostMapping("/successful")
    public void successfulDelivery(@RequestBody String orderId) {
        deliveryService.successfulDelivery(orderId);
    }

    @Override
    @PostMapping("/picked")
    public void pickProducts(@RequestBody String orderId) {
        deliveryService.pickProducts(orderId);
    }

    @Override
    @PostMapping("/failed")
    public void failedDelivery(@RequestBody String orderId) {
        deliveryService.failedDelivery(orderId);
    }

    @Override
    @PostMapping("/cost")
    public BigDecimal calculateDeliveryCost(@RequestBody String deliveryId) {
        return deliveryService.calculateDeliveryCost(deliveryId);
    }

}
