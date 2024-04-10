package com.gamediscovery.GenreMicroService.dto;

import java.util.Date;

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
