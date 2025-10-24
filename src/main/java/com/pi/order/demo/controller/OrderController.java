package com.pi.order.demo.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pi.order.demo.dto.CreateOrderRequest;
import com.pi.order.demo.dto.OrderResponse;
import com.pi.order.demo.dto.UpdateStatusRequest;
import com.pi.order.demo.enums.OrderStatus;
import com.pi.order.demo.mapping.OrderMapper;
import com.pi.order.demo.service.OrderProcessorService;
import com.pi.order.demo.service.OrderQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Orders", description = "Order management APIs")
public class OrderController {
	
	private final OrderProcessorService orderProcessor;
    private final OrderQueryService queries;

    public OrderController(OrderProcessorService orderProcessor, OrderQueryService queries) {
        this.orderProcessor = orderProcessor; 
        this.queries = queries; 
    }
    
    @PostMapping
    @Operation(summary = "Create an order", description =
    		"Create a new order with multiple items")
    public OrderResponse create(@Valid @RequestBody CreateOrderRequest req) {
        var entity = OrderMapper.toEntity(req.customerId(), req.items());
        var saved = orderProcessor.create(entity.getCustomerId(), entity.getItems());
        return OrderMapper.toDto(saved);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order", description =
    		"Get a existing order by order id")
    public OrderResponse get(@PathVariable UUID id) { 
    	return OrderMapper.toDto(queries.getOrder(id)); 
    }

    @GetMapping
    @Operation(summary = "Get List of Orders", description =
    		"Get list of existing orders, optionally by order status")
    public PagedModel<OrderResponse> list(@RequestParam(required = false) OrderStatus status,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "20") int size) {
        var p = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        return new PagedModel<>(queries.listOrders(Optional.ofNullable(status), p).map(OrderMapper::toDto));
    }

    @PatchMapping("/{id}/status")
    @PostMapping
    @Operation(summary = "Update an order", description =
    		"Update an existing order with multiple items")
    public OrderResponse updateStatus(@PathVariable UUID id, @Valid @RequestBody UpdateStatusRequest req) {
        var updated = orderProcessor.updateStatus(id, req.status());
        return OrderMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order", description =
    		"Delete an existing order with multiple items")
    public void cancel(@PathVariable UUID id) { 
    	orderProcessor.cancel(id); 
    }


}
