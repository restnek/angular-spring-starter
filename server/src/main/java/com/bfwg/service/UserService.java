package com.bfwg.service;

import java.util.List;

import com.bfwg.model.persistence.User;
import com.bfwg.model.request.SignUpRequest;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User findById(Long id);

    List<User> findAll(Pageable pageable);

    User save(SignUpRequest user);

    void changePassword(String username, String oldPassword, String newPassword);
}
