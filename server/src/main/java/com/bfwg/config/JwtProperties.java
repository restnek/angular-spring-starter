package com.bfwg.config;

import java.util.UUID;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {
    private String issuer;
    private int expiration = 600;
    private String secret = UUID.randomUUID().toString();
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
    private String header = "Authorization";
    private String cookie = "AUTH-TOKEN";
    private String type = "Bearer";

    public int getExpirationInMilliseconds() {
        return 1000 * expiration;
    }
}
