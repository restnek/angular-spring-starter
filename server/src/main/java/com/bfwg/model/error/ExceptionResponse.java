package com.bfwg.model.error;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExceptionResponse {
    private final ApiErrorType type;
    private final String message;
    private final List<ApiError> errors;

    public ExceptionResponse(ApiErrorType type, String message) {
        this(type, message, null);
    }
}
