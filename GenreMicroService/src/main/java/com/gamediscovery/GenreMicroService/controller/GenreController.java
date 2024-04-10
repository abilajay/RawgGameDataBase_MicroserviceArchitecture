package com.gamediscovery.GenreMicroService.controller;

import com.gamediscovery.GenreMicroService.entity.Genre;
import com.gamediscovery.GenreMicroService.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("genres")
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping()
    public ResponseEntity<List<Genre>> getAllGenre(){
        return new ResponseEntity<>(genreService.getAllGenre(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long id){
        return new ResponseEntity<>(genreService.getGenreById(id), HttpStatus.OK);
    }

    @GetMapping(params = "genreName")
    public ResponseEntity<Genre> getGenreByName(@RequestParam String genreName){
        return new ResponseEntity<>(genreService.getGenreByName(genreName), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre){
        return new ResponseEntity<>(genreService.createGenre(genre), HttpStatus.CREATED);
    }

    @DeleteMapping("/{genreId}")
    public ResponseEntity<String> deleteGenreById(@PathVariable Long genreId){
        genreService.deleteGenreById(genreId);
        return ResponseEntity.ok("Genre deleted successfully");
    }
}
