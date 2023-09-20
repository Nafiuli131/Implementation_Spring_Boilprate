package com.example.spring_project.exception;

public class BadExceptionHandler extends RuntimeException {

    public BadExceptionHandler(String message) {
        super(message);
    }

}
