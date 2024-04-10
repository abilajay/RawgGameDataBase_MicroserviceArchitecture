package com.gamediscovery.gamemicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PublisherNotFoundException extends RuntimeException{

    public PublisherNotFoundException(){
        super("Publisher not found !");
    }

    public PublisherNotFoundException(Long publisherId) {
        super("Publisher not found with Id: " + publisherId);
    }

    public PublisherNotFoundException(String publisherName) {
        super("Publisher not found with Genre name: " + publisherName);
    }

}
