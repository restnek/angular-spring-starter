package com.bfwg.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    private Long id;

    private String username;

    private String password;

    private String firstname;

    private String lastname;
}
