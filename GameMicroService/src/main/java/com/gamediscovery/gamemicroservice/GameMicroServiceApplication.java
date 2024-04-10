package com.gamediscovery.gamemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GameMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameMicroServiceApplication.class, args);
	}

}
