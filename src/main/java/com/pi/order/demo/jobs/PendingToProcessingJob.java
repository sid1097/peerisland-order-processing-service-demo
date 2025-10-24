package com.pi.order.demo.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.pi.order.demo.enums.OrderStatus;
import com.pi.order.demo.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Component
public class PendingToProcessingJob {
	
	private final OrderRepository repo;
    public PendingToProcessingJob(OrderRepository repo) { 
    	this.repo = repo; 
    }
    
    @Scheduled(fixedRate = 300_000)
    @Transactional
    public void bumpPending() {
        repo.findByStatus(OrderStatus.PENDING).forEach(order -> {
            order.setStatus(OrderStatus.PROCESSING);
            repo.save(order);
        });
    }



}
