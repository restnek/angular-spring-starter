package com.bfwg.security.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfwg.model.error.ApiErrorType;
import com.bfwg.model.error.ExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                ApiErrorType.BAD_CREDENTIALS,
                exception.getMessage());
        response.setStatus(exceptionResponse.getType().getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
    }
}
