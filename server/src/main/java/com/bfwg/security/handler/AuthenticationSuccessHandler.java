package com.bfwg.security.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfwg.config.JwtProperties;
import com.bfwg.model.persistence.User;
import com.bfwg.security.TokenHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@RequiredArgsConstructor
public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenHelper tokenHelper;
    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProperties;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        clearAuthenticationAttributes(request);

        User user = (User) authentication.getPrincipal();
        String token = tokenHelper.generateToken(user.getUsername());

        tokenHelper.setToken(response, token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(user));
    }
}
