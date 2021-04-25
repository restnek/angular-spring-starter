package com.bfwg.model.error;

import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class ApiValidationError extends ApiError {
    private final String type;
    private final String field;

    public ApiValidationError(FieldError fieldError) {
        super(fieldError.getDefaultMessage());
        type = fieldError.getCode().toLowerCase();
        field = fieldError.getField();
    }

    public ApiValidationError(String type, String field, String message) {
        super(message);
        this.type = type;
        this.field = field;
    }
}
