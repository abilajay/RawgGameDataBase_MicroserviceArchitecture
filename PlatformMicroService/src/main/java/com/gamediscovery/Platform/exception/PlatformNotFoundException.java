package com.gamediscovery.Platform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlatformNotFoundException extends RuntimeException{
    public PlatformNotFoundException(Long platformId) {
        super("Platform not found with id:"+platformId);
    }

    public PlatformNotFoundException(String platformName) {
        super("Platform not found with id:"+platformName);
    }
}
