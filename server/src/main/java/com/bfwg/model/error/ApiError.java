package com.bfwg.model.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class ApiError {
    private final String message;
}
