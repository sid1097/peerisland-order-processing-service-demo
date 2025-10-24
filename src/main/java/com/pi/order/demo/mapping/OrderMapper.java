package com.pi.order.demo.mapping;

import java.util.List;

import com.pi.order.demo.dto.OrderItemRequest;
import com.pi.order.demo.dto.OrderItemResponse;
import com.pi.order.demo.dto.OrderResponse;
import com.pi.order.demo.entity.Order;
import com.pi.order.demo.entity.OrderItem;

public class OrderMapper {
	
	public static Order toEntity(String customerId, List<OrderItemRequest> items) {
        Order order = new Order();
        order.setCustomerId(customerId);
        items.forEach(req -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setName(req.name());
            orderItem.setProductId(req.productId());
            orderItem.setQuantity(req.quantity());
            orderItem.setUnitPrice(req.unitPrice());
            order.addItem(orderItem);
        });
        return order;
    }

    public static OrderResponse toDto(Order order) {
        List<OrderItemResponse> itemDtos = order.getItems().stream()
        		.map(itemDto -> new OrderItemResponse(
        				itemDto.getId(), 
        				itemDto.getProductId(), 
        				itemDto.getName(), 
        				itemDto.getQuantity(), 
        				itemDto.getUnitPrice(), 
        				itemDto.getLineTotal()
        				)
        		).toList();
        
        return new OrderResponse(
                order.getId(), 
                order.getCustomerId(), 
                order.getStatus(), 
                itemDtos,
                order.getTotalAmount(), 
                order.getCreatedAt(), 
                order.getUpdatedAt()
        );
    }

	
}
