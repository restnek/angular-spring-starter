package com.bfwg.model.error;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ApiErrorType {
    NO_HANDLER(HttpStatus.BAD_REQUEST, "noHandler"),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "unsupportedMediaType"),
    NOT_READABLE_MESSAGE(HttpStatus.BAD_REQUEST, "notReadableMessage"),
    VALIDATION(HttpStatus.BAD_REQUEST, "validation"),
    BAD_CREDENTIALS(HttpStatus.UNAUTHORIZED, "badCredentials"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "unauthorized"),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "accessDenied"),
    RESOURCE_DUPLICATE(HttpStatus.CONFLICT, "resourceDuplicate"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "resourceNotFound");

    private final HttpStatus status;

    @JsonValue
    private final String type;
}
