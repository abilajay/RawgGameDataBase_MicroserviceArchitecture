package com.gamediscovery.gamemicroservice.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Date;
@Getter
public class ErrorResponse {
    private final Date timeStamp;
    private final String errorMessage;
    private final String errorDetails;

    public ErrorResponse(Date timeStamp, String errorMessage, String errorDetails) {
        this.timeStamp = timeStamp;
        this.errorMessage = errorMessage;
        this.errorDetails = errorDetails;
    }
}
