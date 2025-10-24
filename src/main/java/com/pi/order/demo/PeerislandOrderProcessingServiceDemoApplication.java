package com.pi.order.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
@EnableScheduling
public class PeerislandOrderProcessingServiceDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeerislandOrderProcessingServiceDemoApplication.class, args);
	}
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info()
					.title("Orders Processing API")
					.version("1.0.0")
					.description("E-commerce Order Processing API")
					.contact(new Contact()
								.name("Sk Sajid Hassan")
								.email("hassansajid718@gmail.com")
							)
			);
	}

}
