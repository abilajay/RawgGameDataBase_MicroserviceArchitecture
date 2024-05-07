package com.gamediscovery.PublisherMicroService.client;

import com.gamediscovery.PublisherMicroService.external.GamesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Game-Microservice", url = "${game-service.url}")
public interface GameClient {
    @GetMapping("/games")
    GamesResponse getGamesByPublisherId(@RequestParam(name = "publisherId") Long publisherId);
}
