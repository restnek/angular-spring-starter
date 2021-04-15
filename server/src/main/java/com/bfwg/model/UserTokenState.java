package com.bfwg.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTokenState {
    private String accessToken;
    private Long expiresIn;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public UserTokenState(String access_token, long expires_in) {
        this.accessToken = access_token;
        this.expiresIn = expires_in;
    }
}
