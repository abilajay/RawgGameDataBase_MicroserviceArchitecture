package com.gamediscovery.GenreMicroService.service.impl;

import com.gamediscovery.GenreMicroService.clients.GameClient;
import com.gamediscovery.GenreMicroService.entity.Genre;
import com.gamediscovery.GenreMicroService.exception.GenreNotFoundException;
import com.gamediscovery.GenreMicroService.repository.GenreRepository;
import com.gamediscovery.GenreMicroService.service.GenreService;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Genre> getAllGenre() {
        List<Genre> genres = genreRepository.findAll();
        for (Genre genre:
             genres) {
            genre.setGameCount(gameClient.fetchGamesByGenreId(genre.getId()).getGameCount());
        }
        return genres;
    }

    @Override
    public Genre getGenreById(Long id) {
        Optional<Genre> optionalGenre = genreRepository.findById(id);
        Genre genre = optionalGenre.orElseThrow(() -> new GenreNotFoundException(id));
        genre.setGameCount(gameClient.fetchGamesByGenreId(id).getGameCount());
        return genre;
    }

    @Override
    public Genre getGenreByName(String genreName) {
        Optional<Genre> optionalGenre = genreRepository.findByName(genreName);
        return optionalGenre.orElseThrow(() -> new GenreNotFoundException(genreName));
    }

    @Override
    public Genre createGenre(Genre genre) {
        Optional<Genre> existingGenre = genreRepository.findByName(genre.getName());
        if (existingGenre.isEmpty())
            return genreRepository.save(genre);
        else
            return null;
    }

    @Override
    public void deleteGenreById(Long genreId) {
        Optional<Genre> optionalGenre = genreRepository.findById(genreId);
        if (optionalGenre.isPresent())
            genreRepository.deleteById(genreId);
        else throw new GenreNotFoundException(genreId);
    }
}
