package com.gamediscovery.GenreMicroService.service;

import com.gamediscovery.GenreMicroService.dto.GenreResponse;
import com.gamediscovery.GenreMicroService.entity.Genre;

import java.util.List;

public interface GenreService {
    List<GenreResponse> getAllGenre();

    GenreResponse getGenreById(Long id);

    GenreResponse getGenreByName(String genreName);

    Genre createGenre(Genre genre);

    void deleteGenreById(Long genreId);

    Genre getGenreByIdForGame(Long id);

    Genre getGenreByNameForGame(String genreName);
}
