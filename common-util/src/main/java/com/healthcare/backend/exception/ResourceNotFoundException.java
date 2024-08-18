package com.healthcare.backend.exception;

public class ResourceNotFoundException extends RuntimeException {


    public ResourceNotFoundException() {
        this("resource not found.");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}