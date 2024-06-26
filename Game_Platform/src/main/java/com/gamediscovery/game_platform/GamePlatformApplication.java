package com.gamediscovery.game_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GamePlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamePlatformApplication.class, args);
	}

}
