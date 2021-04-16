package com.bfwg.exception;

import java.util.List;
import java.util.stream.Collectors;

import com.bfwg.model.error.ApiError;
import com.bfwg.model.error.ApiValidationError;
import com.bfwg.model.error.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handle(Exception e) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, "No handler found");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpMediaTypeNotSupportedException e) {
        return createResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpMessageNotReadableException e) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, "Not readable message");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handle(MethodArgumentNotValidException e) {
        List<ApiError> errors = e.getFieldErrors().stream()
                .map(ApiValidationError::new)
                .collect(Collectors.toList());
        return createResponseEntity(new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                "Validation failed. Error count: " + e.getFieldErrorCount(),
                errors));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handle(AccessDeniedException e) {
        return createResponseEntity(HttpStatus.FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handle(ResourceAlreadyExistException e) {
        return createResponseEntity(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(ResourceNotFoundException e) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    private ResponseEntity<ExceptionResponse> createResponseEntity(
            HttpStatus status,
            String message) {

        return createResponseEntity(new ExceptionResponse(status, message));
    }

    private ResponseEntity<ExceptionResponse> createResponseEntity(ExceptionResponse response) {
        return new ResponseEntity<>(response, response.getStatus());
    }
}
