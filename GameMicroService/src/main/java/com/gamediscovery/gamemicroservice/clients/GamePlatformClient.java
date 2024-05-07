package com.gamediscovery.gamemicroservice.clients;

import com.gamediscovery.gamemicroservice.dto.GamePlatform;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "GamePlatform-Microservice", url = "${game-platform-service.url}")
public interface GamePlatformClient {

    @PostMapping("/game-platform")
    GamePlatform createRecord(@RequestBody GamePlatform gamePlatform);

    @GetMapping("/game-platform")
    List<GamePlatform> fetchRecordByGameId(@RequestParam Long gameId);

    @GetMapping("/game-platform")
    List<GamePlatform> fetchRecordByPlatformId(@RequestParam Long platformId);
}
