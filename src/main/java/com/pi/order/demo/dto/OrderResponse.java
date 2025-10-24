package com.pi.order.demo.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.pi.order.demo.enums.OrderStatus;

public record OrderResponse(
		UUID id,
        String customerId,
        OrderStatus status,
        List<OrderItemResponse> items,
        BigDecimal totalAmount,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt

) {

}
