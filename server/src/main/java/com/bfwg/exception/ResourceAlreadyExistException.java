package com.bfwg.exception;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String resource, String field, String value) {
        super(resource + " with " + field + " '" + value + "' already exist");
    }
}
