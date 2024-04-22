package com.gamediscovery.GenreMicroService.service.impl;

import com.gamediscovery.GenreMicroService.clients.GameClient;
import com.gamediscovery.GenreMicroService.dto.GenreResponse;
import com.gamediscovery.GenreMicroService.entity.Genre;
import com.gamediscovery.GenreMicroService.exception.GenreNotFoundException;
import com.gamediscovery.GenreMicroService.repository.GenreRepository;
import com.gamediscovery.GenreMicroService.service.GenreService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    private final GameClient gameClient;

    public GenreServiceImpl(GenreRepository genreRepository, GameClient gameClient) {
        this.genreRepository = genreRepository;
        this.gameClient = gameClient;
    }


    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public List<GenreResponse> getAllGenre() {
        List<Genre> genres = genreRepository.findAll();
        List<GenreResponse> genreResponses = new ArrayList<>();
        for (Genre genre:
             genres) {
            GenreResponse genreResponse = new GenreResponse();
            genreResponse.setGenre(genre);
            genreResponse.setGameCount(gameClient.fetchGamesByGenreId(genre.getId()).getGameCount());
            genreResponses.add(genreResponse);
        }
        return genreResponses;
    }


    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public GenreResponse getGenreById(Long id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        Genre genre = optionalGenre.orElseThrow(() -> new GenreNotFoundException(id));
        GenreResponse genreResponse = new GenreResponse();
        genreResponse.setGenre(genre);
        genreResponse.setGameCount(gameClient.fetchGamesByGenreId(genre.getId()).getGameCount());
        return genreResponse;
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public Genre getGenreByIdForGame(Long id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        return optionalGenre.orElseThrow(() -> new GenreNotFoundException(id));
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public Genre getGenreByNameForGame(String genreName) {
        Optional<Genre> optionalGenre = genreRepository.findByName(genreName);
        return optionalGenre.orElseThrow(() -> new GenreNotFoundException(genreName));
    }

    @CircuitBreaker(name = "myCircuitBreaker")
    @Override
    public GenreResponse getGenreByName(String genreName) {
        Optional<Genre> optionalGenre = genreRepository.findByName(genreName);
        Genre genre = optionalGenre.orElseThrow(() -> new GenreNotFoundException(genreName));
        GenreResponse genreResponse = new GenreResponse();
        genreResponse.setGenre(genre);
        genreResponse.setGameCount(gameClient.fetchGamesByGenreId(genre.getId()).getGameCount());
        return genreResponse;
    }


    @Override
    public Genre createGenre(Genre genre) {
        Optional<Genre> existingGenre = genreRepository.findByName(genre.getName());
        return existingGenre.orElseGet(() -> genreRepository.save(genre));
    }

    @Override
    public void deleteGenreById(Long genreId) {
        Optional<Genre> optionalGenre = genreRepository.findById(genreId);
        if (optionalGenre.isPresent())
            genreRepository.deleteById(genreId);
        else throw new GenreNotFoundException(genreId);
    }


}
