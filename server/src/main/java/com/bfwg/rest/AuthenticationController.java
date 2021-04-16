package com.bfwg.rest;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfwg.config.JwtProperties;
import com.bfwg.model.request.PasswordChanger;
import com.bfwg.model.response.UserTokenState;
import com.bfwg.security.TokenHelper;
import com.bfwg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final TokenHelper tokenHelper;
    private final JwtProperties jwtProperties;

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAuthenticationToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        String authToken = tokenHelper.getToken(request);
        if (authToken != null && tokenHelper.canTokenBeRefreshed(authToken)) {
            // TODO check user password last update
            String refreshedToken = tokenHelper.refreshToken(authToken);

            Cookie authCookie = new Cookie(jwtProperties.getCookie(), (refreshedToken));
            authCookie.setPath("/");
            authCookie.setHttpOnly(true);
            authCookie.setMaxAge(jwtProperties.getExpiration());
            // Add cookie to response
            response.addCookie(authCookie);

            UserTokenState userTokenState = new UserTokenState(
                    refreshedToken,
                    jwtProperties.getExpiration());
            return ResponseEntity.ok(userTokenState);
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.accepted().body(userTokenState);
        }
    }

    @PostMapping("/changePassword")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> changePassword(
            @RequestBody PasswordChanger passwordChanger,
            Authentication authentication) {

        userService.changePassword(
                authentication.getName(),
                passwordChanger.getOldPassword(),
                passwordChanger.getNewPassword());
        Map<String, String> body = Map.of("result", "success");
        return ResponseEntity.accepted().body(body);
    }
}
