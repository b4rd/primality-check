package com.demo.primalitycheck.model;

public class ApiError {

    private final String message;

    public ApiError(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}