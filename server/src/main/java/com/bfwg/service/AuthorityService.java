package com.bfwg.service;

import java.util.List;

import com.bfwg.model.Authority;
import com.bfwg.model.UserRoleName;

public interface AuthorityService {
    List<Authority> findByName(UserRoleName name);
}
