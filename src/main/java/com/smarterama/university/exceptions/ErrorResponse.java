package com.smarterama.university.exceptions;

import java.net.URI;

public class ErrorResponse {
    private String message;
    private URI uri;

    public ErrorResponse(String message, URI uri) {
        this.message = message;
        this.uri = uri;
    }
}
