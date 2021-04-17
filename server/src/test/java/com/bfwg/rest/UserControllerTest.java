package com.bfwg.rest;

import com.bfwg.config.JwtProperties;
import com.bfwg.security.TokenHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    public void testFailGetAllUsersIfAccessDenied() throws Exception {
        String token = tokenHelper.generateToken("user");
        String errorMessage = "Access is denied";

        Exception exception = mockMvc
                .perform(get("/api/users")
                        .header(jwtProperties.getHeader(), "Bearer " + token))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(errorMessage)))
                .andReturn()
                .getResolvedException();
        assertThat(exception).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    public void testSuccessGetAllUsersIfAccessGranted() throws Exception {
        String token = tokenHelper.generateToken("admin");

        mockMvc
                .perform(get("/api/users")
                        .header(jwtProperties.getHeader(), "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testFailGetUserByIdIfAccessGranted() throws Exception {
        String token = tokenHelper.generateToken("user");
        String errorMessage = "Access is denied";

        Exception exception = mockMvc
                .perform(get("/api/users/1")
                        .header(jwtProperties.getHeader(), "Bearer " + token))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(errorMessage)))
                .andReturn()
                .getResolvedException();
        assertThat(exception).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    public void testFailGetUserByIdIfAccessDenied() throws Exception {
        String token = tokenHelper.generateToken("admin");
        String expectedJson = "{"
                + "id: 1,"
                + "username: 'user',"
                + "firstname: 'Oliver',"
                + "lastname: 'Smith',"
                + "authorities: [{ id: 1, authority: 'ROLE_USER'}]"
                + "}";

        mockMvc
                .perform(get("/api/users/1")
                        .header(jwtProperties.getHeader(), "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }
}
