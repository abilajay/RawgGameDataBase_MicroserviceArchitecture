package com.gamediscovery.gamemicroservice.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(Long id) {
        super("Game not found with ID: " + id);
    }

    public GameNotFoundException() {
        super("Game not found !");
    }
}
