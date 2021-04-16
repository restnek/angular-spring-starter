package com.bfwg.model.error;

import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class ApiValidationError extends ApiError {
    private final String field;

    public ApiValidationError(FieldError fieldError) {
        super(fieldError.getDefaultMessage());
        field = fieldError.getField();
    }
}
