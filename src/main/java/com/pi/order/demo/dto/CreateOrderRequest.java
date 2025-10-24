package com.pi.order.demo.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateOrderRequest(
		@NotBlank String customerId,
        @Size(min = 1) 
		List<@Valid OrderItemRequest> items
) {
}
