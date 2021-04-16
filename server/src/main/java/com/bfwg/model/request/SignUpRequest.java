package com.bfwg.model.request;

import com.bfwg.model.persistence.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@RequiredArgsConstructor
public class SignUpRequest {
    private final String username;
    private final String password;
    private final String firstname;
    private final String lastname;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username,
                passwordEncoder.encode(password),
                firstname,
                lastname);
    }
}
