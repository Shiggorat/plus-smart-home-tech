package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.commerce.dto.OrderDto;
import ru.yandex.practicum.commerce.dto.PaymentDto;
import ru.yandex.practicum.model.PaymentEntity;
import ru.yandex.practicum.model.PaymentState;

import java.math.BigDecimal;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
    @Mapping(source = "payment.paymentId", target = "paymentId")
    @Mapping(source = "payment.totalPrice", target = "totalPayment")
    @Mapping(source = "payment.deliveryPrice", target = "deliveryTotal")
    @Mapping(source = "payment.productPrice", target = "productTotal")
    @Mapping(source = "feeTotal", target = "feeTotal")
    PaymentDto toDto(PaymentEntity payment, BigDecimal feeTotal);

    default PaymentEntity toEntity(OrderDto order) {
        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentState(PaymentState.PENDING);
        payment.setDeliveryPrice(order.getDeliveryPrice());
        payment.setProductPrice(order.getProductPrice());
        payment.setTotalPrice(order.getTotalPrice());
        payment.setOrderId(order.getOrderId());
        return payment;
    }
}
