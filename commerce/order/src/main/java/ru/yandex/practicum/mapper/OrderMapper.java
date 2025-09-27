package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import ru.yandex.practicum.commerce.dto.AddressDto;
import ru.yandex.practicum.commerce.dto.OrderDto;
import ru.yandex.practicum.commerce.dto.ShoppingCartDto;
import ru.yandex.practicum.commerce.request.CreateNewOrderRequest;
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.OrderEntity;
import ru.yandex.practicum.model.OrderProduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    default OrderEntity toEntity(CreateNewOrderRequest createNewOrderRequest, String username) {
        OrderEntity orderEntity = new OrderEntity();
        ShoppingCartDto cart = createNewOrderRequest.getShoppingCart();
        orderEntity.setProducts(toEntityList(cart.getProducts()));
        orderEntity.setShoppingCartId(cart.getCartId());
        orderEntity.setUsername(username);
        Address address = toEntity(createNewOrderRequest.getDeliveryAddress());
        orderEntity.setDeliveryAddress(address);
        return orderEntity;
    }

    default List<OrderProduct> toEntityList(Map<String, Integer> products) {
        return products.entrySet().stream().map(entry -> {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProductId(entry.getKey());
            orderProduct.setQuantity(entry.getValue());
            return orderProduct;
        }).collect(Collectors.toList());
    }

    @Mapping(source = "products", target = "products", qualifiedByName = "toDtoMap")
    @Mapping(source = "deliveryDetails.deliveryWeight", target = "deliveryWeight")
    @Mapping(source = "deliveryDetails.deliveryVolume", target = "deliveryVolume")
    @Mapping(source = "deliveryDetails.deliveryPrice", target = "deliveryPrice")
    @Mapping(source = "deliveryDetails.deliveryId", target = "deliveryId")
    @Mapping(source = "deliveryDetails.fragile", target = "fragile")
    OrderDto toDto(OrderEntity orderEntity);

    List<OrderDto> toDtoList(List<OrderEntity> orders);

    @Named("toDtoMap")
    default Map<String, Integer> toDtoMap(List<OrderProduct> products) {
        Map<String, Integer> productsMap = new HashMap<>();
        for (OrderProduct product : products) {
            productsMap.put(product.getProductId(), product.getQuantity());
        }
        return productsMap;
    }

    AddressDto toDto(Address address);

    Address toEntity(AddressDto addressDto);
}


