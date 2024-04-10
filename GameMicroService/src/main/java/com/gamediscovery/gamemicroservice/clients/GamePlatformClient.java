package com.gamediscovery.gamemicroservice.clients;

import com.gamediscovery.gamemicroservice.dto.GamePlatform;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "game-platform", url = "localhost:8082")
public interface GamePlatformClient {

    @PostMapping("/game-platform")
    GamePlatform createRecord(@RequestBody GamePlatform gamePlatform);
}
