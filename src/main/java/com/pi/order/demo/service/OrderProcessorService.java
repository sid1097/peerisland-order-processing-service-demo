package com.pi.order.demo.service;

import java.util.List;
import java.util.UUID;

import com.pi.order.demo.entity.Order;
import com.pi.order.demo.entity.OrderItem;
import com.pi.order.demo.enums.OrderStatus;

public interface OrderProcessorService {
	
	Order create(String customerId, List<OrderItem> items);
    Order updateStatus(UUID orderId, OrderStatus newStatus);
    void cancel(UUID orderId);


}
