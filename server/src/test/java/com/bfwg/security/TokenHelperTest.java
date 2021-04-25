package com.bfwg.security;

import java.time.Clock;
import java.time.Duration;

import com.bfwg.config.JwtProperties;
import com.bfwg.service.impl.UserDetailsLoaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class TokenHelperTest {
    private TokenHelper tokenHelper;
    private UserDetailsService userDetailsService;
    private JwtProperties jwtProperties;
    private final Clock clock = Clock.systemDefaultZone();

    @BeforeEach
    public void init() {
        userDetailsService = mock(UserDetailsLoaderService.class);

        jwtProperties = new JwtProperties();
        jwtProperties.setIssuer("test");
        jwtProperties.setExpiration(10);

        tokenHelper = new TokenHelper(userDetailsService, jwtProperties, clock);
    }

    @Test
    public void testCanTokenBeRefreshedIfNotExpire() {
        String token = tokenHelper.generateToken("restnek");
        assertTrue(tokenHelper.canTokenBeRefreshed(token));
    }

    @Test
    public void testCanTokenBeRefreshedIfExpire() {
        String token = tokenHelper.generateToken("restnek");

        tokenHelper.setClock(Clock.offset(
                clock,
                Duration.ofSeconds(jwtProperties.getExpiration())));

        assertFalse(tokenHelper.canTokenBeRefreshed(token));
    }
}
