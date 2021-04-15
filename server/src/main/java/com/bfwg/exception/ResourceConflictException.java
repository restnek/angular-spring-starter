package com.bfwg.exception;

import lombok.Getter;

@Getter
public class ResourceConflictException extends RuntimeException {
    private static final long serialVersionUID = 1791564636123821405L;
    private final Long resourceId;

    public ResourceConflictException(Long resourceId, String message) {
        super(message);
        this.resourceId = resourceId;
    }
}
