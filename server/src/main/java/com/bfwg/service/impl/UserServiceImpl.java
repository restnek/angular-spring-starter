package com.bfwg.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.bfwg.exception.ResourceAlreadyExistException;
import com.bfwg.exception.ResourceNotFoundException;
import com.bfwg.model.persistence.Authority;
import com.bfwg.model.persistence.User;
import com.bfwg.model.persistence.UserRoleName;
import com.bfwg.model.request.SignUpRequest;
import com.bfwg.repository.UserRepository;
import com.bfwg.service.AuthorityService;
import com.bfwg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthorityService authService;
    private final PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('ADMIN')")
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user", id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAll(Pageable pageable) {
        return userRepository
                .findAll(pageable)
                .getContent();
    }

    @Override
    @Transactional
    public User save(SignUpRequest signUpRequest) {
        String username = signUpRequest.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw new ResourceAlreadyExistException("User", "username", username);
        }

        User user = signUpRequest.toUser(passwordEncoder);
        List<Authority> auth = authService.findByName(UserRoleName.ROLE_USER);
        user.setAuthorities(auth);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        userRepository
                .findByUsername(username)
                .map(u -> {
                    u.setPassword(passwordEncoder.encode(newPassword));
                    return userRepository.save(u);
                })
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No user found with username " + username));
    }
}
