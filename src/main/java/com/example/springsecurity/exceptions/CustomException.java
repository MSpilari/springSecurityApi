package com.example.springsecurity.exceptions;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CustomException(HttpStatus statusCode, String message) {
        super(message);
        this.httpStatus = statusCode;
    }

    public HttpStatus getStatus() {
        return this.httpStatus;
    }

}
