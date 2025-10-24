package com.pi.order.demo.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
		Long id, 
		String productId, 
		String name, 
		int quantity, 
		BigDecimal unitPrice, 
		BigDecimal lineTotal
) {

}
