package com.gamediscovery.gamemicroservice.handler;

import com.gamediscovery.gamemicroservice.exception.GameNotFoundException;
import com.gamediscovery.gamemicroservice.exception.GenreNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()){
            case 400:
                return new BadRequestException();
            case 404:
                if(methodKey.contains("getGenreById") | methodKey.contains("getGenreByName"))
                    return new GenreNotFoundException();
                else return new GameNotFoundException();
            default:
                return new Exception("Exception while getting resource details");
        }
    }
}
