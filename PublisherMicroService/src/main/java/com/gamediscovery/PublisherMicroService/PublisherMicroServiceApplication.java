package com.gamediscovery.PublisherMicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PublisherMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublisherMicroServiceApplication.class, args);
	}

}
