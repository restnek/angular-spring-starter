package com.bfwg.service;

import java.util.List;

import com.bfwg.model.User;
import com.bfwg.model.UserRequest;

public interface UserService {
    void resetCredentials();

    User findById(Long id);

    User findByUsername(String username);

    List<User> findAll();

    User save(UserRequest user);
}
