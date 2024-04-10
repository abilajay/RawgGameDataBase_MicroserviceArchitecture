package com.gamediscovery.GenreMicroService.service;

import com.gamediscovery.GenreMicroService.entity.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenre();

    Genre getGenreById(Long id);

    Genre getGenreByName(String genreName);

    Genre createGenre(Genre genre);

    void deleteGenreById(Long genreId);
}
