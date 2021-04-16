package com.bfwg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handle(ResourceAlreadyExistException e) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.CONFLICT, e.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }
}
