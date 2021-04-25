package com.bfwg.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.bfwg.model.persistence.User;
import com.bfwg.validation.LatinAlphanumeric;
import com.bfwg.validation.LatinLetters;
import com.bfwg.validation.Password;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@RequiredArgsConstructor
public class SignUpRequest {
    @NotEmpty(message = "Username can't be empty")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters long")
    @LatinAlphanumeric(message = "Usernames must only contain latin alphanumeric characters")
    private final String username;

    @Password(message = "Password must contain at least one digit, special symbol, "
            + "upper and lower character")
    @Size(max = 100, message = "Password must be less than 100 characters")
    private final String password;

    @NotEmpty(message = "Firstname can't be empty")
    @LatinLetters(message = "Firstname must contain only latin letters")
    @Size(max = 50, message = "Firstname must be less than 50 characters")
    private final String firstname;

    @NotEmpty(message = "Lastname can't be empty")
    @LatinLetters(message = "Lastname must contain only latin letters")
    @Size(max = 50, message = "Lastname must be less than 50 characters")
    private final String lastname;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(
                username,
                passwordEncoder.encode(password),
                firstname,
                lastname);
    }
}
