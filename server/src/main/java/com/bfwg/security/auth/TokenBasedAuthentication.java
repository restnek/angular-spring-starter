package com.bfwg.security.auth;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class TokenBasedAuthentication extends AbstractAuthenticationToken {
    private final UserDetails userDetails;
    private final String token;

    public TokenBasedAuthentication(UserDetails userDetails, String token) {
        super(userDetails.getAuthorities());
        this.userDetails = userDetails;
        this.token = token;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public UserDetails getPrincipal() {
        return userDetails;
    }
}
