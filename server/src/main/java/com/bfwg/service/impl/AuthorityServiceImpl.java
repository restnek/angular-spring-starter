package com.bfwg.service.impl;

import java.util.ArrayList;
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
    public List<Authority> findById(Long id) {
        // TODO Auto-generated method stub

        Authority auth = this.authorityRepository.getOne(id);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }

    @Override
    public List<Authority> findByName(UserRoleName name) {
        // TODO Auto-generated method stub
        Authority auth = this.authorityRepository.findByName(name);
        List<Authority> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }
}
