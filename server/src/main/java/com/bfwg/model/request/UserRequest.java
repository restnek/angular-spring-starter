package com.bfwg.model.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRequest {
    private final Long id;
    private final String username;
    private final String password;
    private final String firstname;
    private final String lastname;
}
