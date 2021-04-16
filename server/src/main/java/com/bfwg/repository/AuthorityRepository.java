package com.bfwg.repository;

import java.util.Optional;

import com.bfwg.model.Authority;
import com.bfwg.model.UserRoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(UserRoleName name);
}
