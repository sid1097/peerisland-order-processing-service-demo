package com.pi.order.demo.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pi.order.demo.entity.Order;
import com.pi.order.demo.entity.OrderItem;
import com.pi.order.demo.enums.OrderStatus;
import com.pi.order.demo.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderProcessorService, OrderQueryService{
	
	private final OrderRepository repo;

    public OrderServiceImpl(OrderRepository repo) { 
    	this.repo = repo; 
    }


	@Override
	public Order getOrder(UUID orderId) {
		return repo.findById(orderId)
				.orElseThrow(() -> new NoSuchElementException("Order not found"));
	}

	@Override
	public Page<Order> listOrders(Optional<OrderStatus> status, Pageable pageable) {

		if (status.isPresent()) {
            return repo.findByStatus(status.get(), pageable);
        }
		return repo.findAll(pageable);
		
    }

	@Override
	public Order create(String customerId, List<OrderItem> items) {
		Order order = new Order();
		order.setCustomerId(customerId);
        items.forEach(order::addItem);
        return repo.save(order);
	}

	@Override
	public Order updateStatus(UUID orderId, OrderStatus newStatus) {
		Order order = getOrder(orderId);
        if (newStatus == OrderStatus.CANCELED) {
            order.cancel();
        } else {
            order.updateStatus(newStatus);
        }
        return repo.save(order);
	}

	@Override
	public void cancel(UUID orderId) {
		Order order = getOrder(orderId);
        order.cancel();
        repo.save(order);
	}

}
