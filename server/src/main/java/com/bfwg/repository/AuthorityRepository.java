package com.bfwg.repository;

import java.util.Optional;

import com.bfwg.model.persistence.Authority;
import com.bfwg.model.persistence.UserRoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByName(UserRoleName name);
}
