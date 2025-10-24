package com.pi.order.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import com.pi.order.demo.dto.CreateOrderRequest;
import com.pi.order.demo.dto.OrderItemRequest;
import com.pi.order.demo.dto.OrderResponse;
import com.pi.order.demo.enums.OrderStatus;

@SpringBootTest(classes = PeerislandOrderProcessingServiceDemoApplication.class, 
webEnvironment =SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {

	@LocalServerPort int port;
	
	@Test
	void create_and_fetch() {
		var client = RestClient.builder().baseUrl("http://localhost:" +
				port).build();
		var req = new CreateOrderRequest(
				"cust-1",
				List.of(new OrderItemRequest("SKU-1","Item 1",1,new
						BigDecimal("5.50")))
				);
		var created = client.post().uri("/api/v1/orders").contentType(MediaType.APPLICATION_JSON)
				.body(req).retrieve().body(OrderResponse.class);
				assertThat(created.status()).isEqualTo(OrderStatus.PENDING);
				var fetched = client.get().uri("/api/v1/orders/"+created.id()).retrieve().body(OrderResponse.class);
						assertThat(fetched.id()).isEqualTo(created.id());
	}

}
