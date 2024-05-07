package com.gamediscovery.game_platform.clients;

import com.gamediscovery.game_platform.entity.Platform;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "Platform-Microservice", url = "${platform-service.url}")
public interface PlatformClient {
    @GetMapping("/platforms")
    List<Platform> createMultiplePlatform(@RequestBody List<Platform> platforms);

    @GetMapping("/platforms/{platformId}")
    Platform getPlatformById(@PathVariable Long platformId);

    @GetMapping(value = "/platforms", params = "platformName")
    Platform getPlatformByName(@RequestParam String platformName);

}
