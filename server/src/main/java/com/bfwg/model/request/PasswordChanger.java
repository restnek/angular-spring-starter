package com.bfwg.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.bfwg.validation.Password;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PasswordChanger {
    @NotBlank(message = "Old password can't be blank")
    private final String oldPassword;

    @Password(message = "Password must contain at least one digit, special symbol, "
            + "upper and lower character")
    @Size(max = 100, message = "Password must be less than 100 characters")
    private final String newPassword;
}
