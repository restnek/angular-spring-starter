package com.bfwg.service.impl;

import java.util.Collections;
import java.util.List;

import com.bfwg.model.Authority;
import com.bfwg.model.UserRoleName;
import com.bfwg.repository.AuthorityRepository;
import com.bfwg.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;

    @Override
    public List<Authority> findByName(UserRoleName name) {
        return authorityRepository
                .findByName(name)
                .map(List::of)
                .orElse(Collections.emptyList());
    }
}
