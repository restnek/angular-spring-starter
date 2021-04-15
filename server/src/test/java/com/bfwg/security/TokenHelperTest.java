package com.bfwg.security;

import io.jsonwebtoken.Jwts;
import org.joda.time.DateTimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class TokenHelperTest {
    private TokenHelper tokenHelper;

    @BeforeEach
    public void init() {
        tokenHelper = new TokenHelper();
        DateTimeUtils.setCurrentMillisFixed(20L);
        ReflectionTestUtils.setField(tokenHelper, "EXPIRES_IN", 1);
        ReflectionTestUtils.setField(tokenHelper, "SECRET", "mySecret");
    }

    @Test
    public void testGenerateTokenExpired() {
        // expected ExpiredJwtException
        String token = tokenHelper.generateToken("fanjin");
        Jwts.parser()
                .setSigningKey("mySecret")
                .parseClaimsJws(token)
                .getBody();
    }
}
