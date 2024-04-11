package com.gamediscovery.GenreMicroService.clients;

import com.gamediscovery.GenreMicroService.external.GamesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Game-Microservice")
public interface GameClient {
    @GetMapping("/games")
    GamesResponse fetchGamesByGenreId(@RequestParam(name = "genreId") Long genreId);
}
