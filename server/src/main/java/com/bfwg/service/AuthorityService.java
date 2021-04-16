package com.bfwg.service;

import java.util.List;

import com.bfwg.model.persistence.Authority;
import com.bfwg.model.persistence.UserRoleName;

public interface AuthorityService {
    List<Authority> findByName(UserRoleName name);
}
