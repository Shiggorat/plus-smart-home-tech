package ru.yandex.practicum.service;

import ru.yandex.practicum.commerce.dto.OrderDto;
import ru.yandex.practicum.commerce.request.CreateNewOrderRequest;
import ru.yandex.practicum.commerce.request.ProductReturnRequest;

import java.util.List;

public interface OrderService {
    OrderDto createNewOrder(String username, CreateNewOrderRequest request);

    List<OrderDto> getUserOrders(String username);

    OrderDto returnProducts(ProductReturnRequest request);

    OrderDto payOrder(String orderId);

    OrderDto successfulPayment(String orderId);

    OrderDto paymentFailed(String orderId);

    OrderDto successfulDelivery(String orderId);

    OrderDto deliveryFailed(String orderId);

    OrderDto completeOrder(String orderId);

    OrderDto calculateTotal(String orderId);

    OrderDto calculateDelivery(String orderId);

    OrderDto assembleOrder(String orderId);

    OrderDto assemblyFailed(String orderId);
}
