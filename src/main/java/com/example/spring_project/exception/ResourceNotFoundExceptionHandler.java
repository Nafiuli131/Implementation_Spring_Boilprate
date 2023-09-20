package com.example.spring_project.exception;

public class ResourceNotFoundExceptionHandler extends RuntimeException {
    public ResourceNotFoundExceptionHandler(String message) {
        super(message);
    }
}
