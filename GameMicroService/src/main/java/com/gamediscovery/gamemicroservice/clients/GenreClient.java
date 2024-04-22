package com.gamediscovery.gamemicroservice.clients;

import com.gamediscovery.gamemicroservice.external.Genre;
import com.gamediscovery.gamemicroservice.external.GenreResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Genre-Microservice")
public interface GenreClient {

    @GetMapping(value = "/genres", params = "genreName")
    ResponseEntity<GenreResponse> getGenreByName(@RequestParam String genreName);

    @GetMapping("/genres/{genreId}")
    ResponseEntity<GenreResponse> getGenreById(@PathVariable Long genreId);

    @GetMapping("/genres/genre/{genreId}")
    ResponseEntity<Genre> getGenreByIdForGame(@PathVariable Long genreId);

    @GetMapping(value = "/genres/genre", params = "genreName")
    ResponseEntity<Genre> getGenreByNameForGame(@RequestParam String genreName);
}
