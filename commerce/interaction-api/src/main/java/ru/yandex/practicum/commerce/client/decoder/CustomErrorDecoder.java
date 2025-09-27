package ru.yandex.practicum.commerce.client.decoder;

import feign.Response;
import feign.codec.ErrorDecoder;
import ru.yandex.practicum.commerce.exception.NoOrderFoundException;
import ru.yandex.practicum.commerce.exception.ProductInShoppingCartLowQuantityInWarehouseException;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == 404) {
            return new NoOrderFoundException("Order is not found");
        }

        return switch (methodKey) {
            case "PaymentOperations#createPayment(OrderDto)",
                    "PaymentOperations#getTotalCost(OrderDto)",
                    "DeliveryOperations#successfulDelivery(String)",
                    "DeliveryOperations#failedDelivery(String)",
                    "DeliveryOperations#calculateDeliveryCost(String)" -> new NoOrderFoundException("Payment error.");
            case "WarehouseOperations#checkProductsQuantity(ShoppingCartDto)", "WarehouseOperations#assemblyProducts(AssemblyProductsForOrderRequest)" ->
                    new ProductInShoppingCartLowQuantityInWarehouseException(
                            "Недостаточное количество товара на складе");
            default -> defaultDecoder.decode(methodKey, response);
        };

    }

}
