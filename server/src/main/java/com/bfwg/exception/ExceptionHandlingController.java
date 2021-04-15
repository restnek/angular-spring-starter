package com.bfwg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<ExceptionResponse> resourceConflict(ResourceConflictException e) {
        ExceptionResponse response = new ExceptionResponse(HttpStatus.CONFLICT, e.getMessage());
        return new ResponseEntity<>(response, response.getStatus());
    }
}
