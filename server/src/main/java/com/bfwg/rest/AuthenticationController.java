package com.bfwg.rest;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import com.bfwg.config.JwtProperties;
import com.bfwg.model.persistence.User;
import com.bfwg.model.request.PasswordChanger;
import com.bfwg.model.request.SignUpRequest;
import com.bfwg.model.response.UserTokenState;
import com.bfwg.security.TokenHelper;
import com.bfwg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    private final TokenHelper tokenHelper;
    private final JwtProperties jwtProperties;

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> addUser(@RequestBody SignUpRequest signUpRequest) {
        User savedUser = userService.save(signUpRequest);

        URI location = MvcUriComponentsBuilder
                .fromMethodCall(MvcUriComponentsBuilder
                        .on(UserController.class)
                        .getUserById(savedUser.getId()))
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(savedUser, headers, HttpStatus.CREATED);
    }

    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('USER')")
    public void changePassword(
            @RequestBody PasswordChanger passwordChanger,
            Authentication authentication) {

        userService.changePassword(
                authentication.getName(),
                passwordChanger.getOldPassword(),
                passwordChanger.getNewPassword());
    }

    @GetMapping("/whoami")
    @PreAuthorize("hasRole('USER')")
    public User findOutWhoAmI(Authentication authentication) {
        return (User) authentication.getPrincipal();
    }

    // TODO check user password last update
    @GetMapping("/refresh")
    public ResponseEntity<UserTokenState> refreshAuthToken(HttpServletRequest request) {
        String authToken = tokenHelper.getToken(request);
        if (authToken != null && tokenHelper.canTokenBeRefreshed(authToken)) {
            String refreshedToken = tokenHelper.refreshToken(authToken);
            ResponseCookie authCookie = tokenHelper.generateCookieWithToken(refreshedToken);
            UserTokenState userTokenState = new UserTokenState(
                    refreshedToken,
                    jwtProperties.getExpiration());

            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE, authCookie.toString())
                    .body(userTokenState);
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.accepted().body(userTokenState);
        }
    }
}
