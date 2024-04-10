package com.gamediscovery.PublisherMicroService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PublisherNotFoundException extends RuntimeException{
    public PublisherNotFoundException(Long publisherId) {
        super("Publisher not found with id: "+publisherId);
    }

    public PublisherNotFoundException(String publisherName) {
        super("Publisher not found with name: "+publisherName);
    }
}
