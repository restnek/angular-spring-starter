package com.bfwg.service;

import com.bfwg.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest extends AbstractTest {
    @Autowired
    public UserService userService;

    @Test
    public void testFindAllWithoutUser() {
        assertThrows(AccessDeniedException.class, userService::findAll);
    }

    @Test
    public void testFindAllWithUser() {
        mockAuthenticatedUser(buildTestUser());
        assertThrows(AccessDeniedException.class, userService::findAll);
    }

    @Test
    public void testFindAllWithAdmin() {
        mockAuthenticatedUser(buildTestAdmin());
        userService.findAll();
    }

    @Test
    public void testFindByIdWithoutUser() {
        assertThrows(AccessDeniedException.class, () -> userService.findById(1L));
    }

    @Test
    public void testFindByIdWithUser() {
        mockAuthenticatedUser(buildTestUser());
        assertThrows(AccessDeniedException.class, () -> userService.findById(1L));
    }

    @Test
    public void testFindByIdWithAdmin() {
        mockAuthenticatedUser(buildTestAdmin());
        userService.findById(1L);
    }

    @Test
    public void testFindByUsernameWithoutUser() {
        userService.findByUsername("user");
    }

    @Test
    public void testFindByUsernameWithUser() {
        mockAuthenticatedUser(buildTestUser());
        userService.findByUsername("user");
    }

    @Test
    public void testFindByUsernameWithAdmin() {
        mockAuthenticatedUser(buildTestAdmin());
        userService.findByUsername("user");
    }
}
