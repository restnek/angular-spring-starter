package com.bfwg.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PasswordChanger {
    private final String oldPassword;
    private final String newPassword;
}
