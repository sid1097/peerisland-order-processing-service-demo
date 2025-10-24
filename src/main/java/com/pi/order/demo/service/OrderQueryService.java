package com.pi.order.demo.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pi.order.demo.entity.Order;
import com.pi.order.demo.enums.OrderStatus;

public interface OrderQueryService {
	
	Order getOrder(UUID orderId);
    Page<Order> listOrders(Optional<OrderStatus> status, Pageable pageable);

}
