package com.pi.order.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pi.order.demo.entity.Order;
import com.pi.order.demo.enums.OrderStatus;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>{
	
	Page<Order> findByStatus(OrderStatus status, Pageable pageable);
	
	List<Order> findByStatus(OrderStatus status);

}
