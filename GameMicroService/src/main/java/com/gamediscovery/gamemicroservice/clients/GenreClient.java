package com.gamediscovery.gamemicroservice.clients;

import com.gamediscovery.gamemicroservice.external.Genre;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "genre-microservice", url = "localhost:8081")
public interface GenreClient {

    @GetMapping(value = "/genres", params = "genreName")
    Genre getGenreByName(@RequestParam String genreName);

    @GetMapping("/genres/{genreId}")
    ResponseEntity<Genre> getGenreById(@PathVariable Long genreId);
}
