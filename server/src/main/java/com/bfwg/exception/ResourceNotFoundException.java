package com.bfwg.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Object id) {
        super("Unable to find " + resource + " with id " + id);
    }
}
