package com.gamediscovery.GenreMicroService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GenreMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenreMicroServiceApplication.class, args);
	}

}
