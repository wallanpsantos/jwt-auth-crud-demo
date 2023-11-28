package com.jwtauthcruddemo.exceptions;

import org.springframework.http.HttpStatus;

public class AppLoginException extends RuntimeException {

    private HttpStatus status;

    public AppLoginException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
