package com.gamediscovery.GenreMicroService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GenreNotFoundException extends RuntimeException{
    public GenreNotFoundException(Long genreId) {
        super("Genre not found with Id: " + genreId);
    }

    public GenreNotFoundException(String genreName) {
        super("Genre not found with Genre name: " + genreName);
    }
}
