package com.pi.order.demo.dto;

import com.pi.order.demo.enums.OrderStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateStatusRequest(@NotNull OrderStatus status) {

}
