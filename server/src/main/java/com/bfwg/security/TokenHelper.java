package com.bfwg.security;

import java.time.Clock;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.bfwg.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
@AllArgsConstructor
public class TokenHelper {
    private final UserDetailsService userDetailsService;
    private final JwtProperties jwtProperties;
    private Clock clock;

    @Autowired
    public TokenHelper(UserDetailsService userDetailsService, JwtProperties jwtProperties) {
        this(userDetailsService, jwtProperties, Clock.systemDefaultZone());
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public String generateToken(String username) {
        Date issuedAt = getCurrentDate();
        Date expiration = getExpirationDate(issuedAt);
        Claims claims = Jwts.claims()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .setIssuer(jwtProperties.getIssuer());
        return generateToken(claims);
    }

    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }

        Date issuedAt = getCurrentDate();
        Date expiration = getExpirationDate(issuedAt);
        claims.setIssuedAt(issuedAt).setExpiration(expiration);
        return generateToken(claims);
    }

    private String generateToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(jwtProperties.getSignatureAlgorithm(), jwtProperties.getSecret())
                .compact();
    }

    public boolean canTokenBeRefreshed(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return false;
            }

            Date expirationDate = claims.getExpiration();
            String username = getUsernameFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            return expirationDate.compareTo(getCurrentDate()) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private Date getCurrentDate() {
        return Date.from(clock.instant());
    }

    private Date getExpirationDate(Date issuedAt) {
        return new Date(issuedAt.getTime() + this.jwtProperties.getExpirationInMilliseconds());
    }

    public String getToken(HttpServletRequest request) {
        String token = getAuthCookie(request);
        return token != null ? token : getAuthHeader(request);
    }

    private String getAuthCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtProperties.getCookie());
        return cookie != null ? cookie.getValue() : null;
    }

    private String getAuthHeader(HttpServletRequest request) {
        String header = request.getHeader(jwtProperties.getHeader());
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    private Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public ResponseCookie generateCookieWithToken(String token) {
        return ResponseCookie.from(jwtProperties.getCookie(), token)
                .httpOnly(true)
                .maxAge(jwtProperties.getExpiration())
                .path("/")
                .build();
    }
}
