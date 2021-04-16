package com.bfwg.service;

import java.util.List;

import com.bfwg.model.persistence.User;
import com.bfwg.model.request.UserRequest;

public interface UserService {
    void resetCredentials();

    User findById(Long id);

    User findByUsername(String username);

    List<User> findAll();

    User save(UserRequest user);

    void changePassword(String username, String oldPassword, String newPassword);
}
