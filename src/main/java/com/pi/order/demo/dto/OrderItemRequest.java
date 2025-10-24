package com.pi.order.demo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record OrderItemRequest(
		@NotBlank String productId,
        @NotBlank String name,
        @Positive int quantity,
        @Positive BigDecimal unitPrice
) {}
