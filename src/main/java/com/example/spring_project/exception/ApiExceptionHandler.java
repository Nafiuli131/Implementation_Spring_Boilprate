package com.example.spring_project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {BadExceptionHandler.class})
    public ResponseEntity<Object> handleBadException(BadExceptionHandler exception) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        ApiException apiException =
                new ApiException(exception.getMessage(), badRequest, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, badRequest);
    }

    @ExceptionHandler(value = {ResourceNotFoundExceptionHandler.class})
    public ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundExceptionHandler exception) {
        HttpStatus notFoundRequest = HttpStatus.NOT_FOUND;
        ApiException apiException =
                new ApiException(exception.getMessage(), notFoundRequest, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(apiException, notFoundRequest);
    }
}
