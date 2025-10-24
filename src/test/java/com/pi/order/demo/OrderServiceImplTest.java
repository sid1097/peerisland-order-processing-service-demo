package com.pi.order.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.pi.order.demo.entity.Order;
import com.pi.order.demo.entity.OrderItem;
import com.pi.order.demo.enums.OrderStatus;
import com.pi.order.demo.repository.OrderRepository;
import com.pi.order.demo.service.OrderServiceImpl;

@SpringBootTest
public class OrderServiceImplTest {
	
	@Test
	void cancel_only_when_pending() {
		var repo = Mockito.mock(OrderRepository.class);
		var svc = new OrderServiceImpl(repo);
		var id = UUID.randomUUID();
		var order = new Order();
		order.setId(id);
		order.setStatus(OrderStatus.PENDING);
		when(repo.findById(id)).thenReturn(Optional.of(order));
		svc.cancel(id);
		assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCELED);
		verify(repo).save(order);
	}
	
	@Test
	void create_computes_total() {
		var repo = Mockito.mock(OrderRepository.class);
		var svc = new OrderServiceImpl(repo);
		var item = new OrderItem();
		item.setName("x"); item.setProductId("p1"); item.setQuantity(2);
		item.setUnitPrice(new BigDecimal("10.00"));
		var saved = new Order(); saved.setId(UUID.randomUUID());
		saved.addItem(item);
		when(repo.save(any())).thenReturn(saved);
		var out = svc.create("cust1", List.of(item));
		assertThat(out.getTotalAmount()).isEqualByComparingTo("20.00");
	}

}
