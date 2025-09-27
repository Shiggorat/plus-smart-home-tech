package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.commerce.contract.order.OrderOperations;
import ru.yandex.practicum.commerce.dto.OrderDto;
import ru.yandex.practicum.commerce.request.CreateNewOrderRequest;
import ru.yandex.practicum.commerce.request.ProductReturnRequest;
import ru.yandex.practicum.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController implements OrderOperations {

    private final OrderService orderService;

    @Override
    @PutMapping
    public OrderDto createNewOrder(@RequestParam String username, @RequestBody @Valid CreateNewOrderRequest request) {
        return orderService.createNewOrder(username, request);
    }

    @Override
    @GetMapping
    public List<OrderDto> getUserOrders(@RequestParam String username) {
        return orderService.getUserOrders(username);
    }

    @Override
    @PostMapping("/return")
    public OrderDto returnProducts(@RequestBody @Valid ProductReturnRequest request) {
        return orderService.returnProducts(request);
    }

    @Override
    @PostMapping("/payment")
    public OrderDto payOrder(@RequestBody String orderId) {
        return orderService.payOrder(orderId);
    }

    @Override
    @PostMapping("/payment/failed")
    public OrderDto paymentFailed(@RequestBody String orderId) {
        return orderService.paymentFailed(orderId);
    }

    @Override
    @PostMapping("/payment/success")
    public OrderDto successfulPayment(@RequestBody String orderId) {
        return orderService.successfulPayment(orderId);
    }

    @Override
    @PostMapping("/delivery/success")
    public OrderDto successfulDelivery(@RequestBody String orderId) {
        return orderService.successfulDelivery(orderId);
    }

    @Override
    @PostMapping("/delivery/failed")
    public OrderDto deliveryFailed(@RequestBody String orderId) {
        return orderService.deliveryFailed(orderId);
    }

    @Override
    @PostMapping("/completed")
    public OrderDto completeOrder(@RequestBody String orderId) {
        return orderService.completeOrder(orderId);
    }

    @Override
    @PostMapping("/calculate/total")
    public OrderDto calculateTotal(@RequestBody String orderId) {
        return orderService.calculateTotal(orderId);
    }

    @Override
    @PostMapping("/calculate/delivery")
    public OrderDto calculateDelivery(@RequestBody String orderId) {
        return orderService.calculateDelivery(orderId);
    }

    @Override
    @PostMapping("/assembly")
    public OrderDto assembleOrder(@RequestBody String orderId) {
        return orderService.assembleOrder(orderId);
    }

    @Override
    @PostMapping("/assembly/failed")
    public OrderDto assemblyFailed(@RequestBody String orderId) {
        return orderService.assemblyFailed(orderId);
    }
}
