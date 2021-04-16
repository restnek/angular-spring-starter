package com.bfwg.model.error;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private final HttpStatus status;
    private final String message;
    private final List<ApiError> errors;

    public ExceptionResponse(HttpStatus status, String message) {
        this(status, message, null);
    }
}
