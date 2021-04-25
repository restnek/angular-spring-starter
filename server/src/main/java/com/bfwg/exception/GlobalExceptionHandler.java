package com.bfwg.exception;

import java.util.List;
import java.util.stream.Collectors;

import com.bfwg.model.error.ApiError;
import com.bfwg.model.error.ApiErrorType;
import com.bfwg.model.error.ApiValidationError;
import com.bfwg.model.error.ExceptionResponse;
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
        return createResponseEntity(ApiErrorType.NO_HANDLER, "No handler found");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpMediaTypeNotSupportedException e) {
        return createResponseEntity(ApiErrorType.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handle(HttpMessageNotReadableException e) {
        return createResponseEntity(ApiErrorType.NOT_READABLE_MESSAGE, "Not readable message");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handle(MethodArgumentNotValidException e) {
        List<ApiError> errors = e.getFieldErrors().stream()
                .map(ApiValidationError::new)
                .collect(Collectors.toList());
        return createResponseEntity(new ExceptionResponse(
                ApiErrorType.VALIDATION,
                "Validation failed. Error count: " + e.getFieldErrorCount(),
                errors));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handle(AccessDeniedException e) {
        return createResponseEntity(ApiErrorType.ACCESS_DENIED, e.getMessage());
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handle(ResourceAlreadyExistException e) {
        return createResponseEntity(new ExceptionResponse(
                ApiErrorType.RESOURCE_DUPLICATE,
                "Resource duplicate",
                List.of(new ApiValidationError("duplicate", e.getField(), e.getMessage()))));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handle(ResourceNotFoundException e) {
        return createResponseEntity(ApiErrorType.RESOURCE_NOT_FOUND, e.getMessage());
    }

    private ResponseEntity<ExceptionResponse> createResponseEntity(
            ApiErrorType type,
            String message) {

        return createResponseEntity(new ExceptionResponse(type, message));
    }

    private ResponseEntity<ExceptionResponse> createResponseEntity(ExceptionResponse response) {
        return new ResponseEntity<>(response, response.getType().getStatus());
    }
}
