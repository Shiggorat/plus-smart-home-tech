package ru.yandex.practicum.commerce.contract.order;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.commerce.dto.OrderDto;
import ru.yandex.practicum.commerce.request.CreateNewOrderRequest;
import ru.yandex.practicum.commerce.request.ProductReturnRequest;

import java.util.List;

public interface OrderOperations {
    @PutMapping
    OrderDto createNewOrder(@RequestParam String username, @RequestBody @Valid CreateNewOrderRequest request);

    @GetMapping
    List<OrderDto> getUserOrders(@RequestParam String username);

    @PostMapping("/return")
    OrderDto returnProducts(@RequestBody @Valid ProductReturnRequest request);

    @PostMapping("/payment")
    OrderDto payOrder(@RequestBody String orderId);

    @PostMapping("/payment/failed")
    OrderDto paymentFailed(@RequestBody String orderId);

    @PostMapping("/payment/success")
    OrderDto successfulPayment(@RequestBody String orderId);

    @PostMapping("/delivery/success")
    OrderDto successfulDelivery(@RequestBody String orderId);

    @PostMapping("/delivery/failed")
    OrderDto deliveryFailed(@RequestBody String orderId);

    @PostMapping("/completed")
    OrderDto completeOrder(@RequestBody String orderId);

    @PostMapping("/calculate/total")
    OrderDto calculateTotal(@RequestBody String orderId);

    @PostMapping("/calculate/delivery")
    OrderDto calculateDelivery(@RequestBody String orderId);

    @PostMapping("/assembly")
    OrderDto assembleOrder(@RequestBody String orderId);

    @PostMapping("/assembly/failed")
    OrderDto assemblyFailed(@RequestBody String orderId);
}
