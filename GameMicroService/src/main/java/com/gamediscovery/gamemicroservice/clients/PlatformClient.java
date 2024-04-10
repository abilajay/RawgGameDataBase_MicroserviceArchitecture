package com.gamediscovery.gamemicroservice.clients;

import com.gamediscovery.gamemicroservice.external.Platform;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "platform-microservice", url = "localhost:8083")
public interface PlatformClient {
    @GetMapping("/platforms")
    List<Platform> createMultiplePlatform(@RequestBody List<Platform> platforms);
}
