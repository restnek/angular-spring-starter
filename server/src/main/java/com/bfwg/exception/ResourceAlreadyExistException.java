package com.bfwg.exception;

import lombok.Getter;

@Getter
public class ResourceAlreadyExistException extends RuntimeException {
    private final String resource;
    private final String field;
    private final String value;

    public ResourceAlreadyExistException(String resource, String field, String value) {
        super(resource + " with " + field + " '" + value + "' already exist");
        this.resource = resource;
        this.field = field;
        this.value = value;
    }
}
