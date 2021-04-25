package com.bfwg.rest;

import com.bfwg.config.JwtProperties;
import com.bfwg.exception.ResourceAlreadyExistException;
import com.bfwg.model.request.PasswordChanger;
import com.bfwg.model.request.SignUpRequest;
import com.bfwg.model.response.UserTokenState;
import com.bfwg.security.TokenHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFailSignUpIfDataInvalid() throws Exception {
        String invalidPayload = "{ \"username\": \"test_user\", \"password\": \"test\" }";
        String validationErrorMessage = "Validation failed";
        String firstnameErrorMessage = "Firstname can't be empty";
        String passwordErrorMessage = "Password must contain at least one digit, special symbol, "
                + "upper and lower character";

        Exception exception = mockMvc
                .perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidPayload))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(allOf(
                        containsString(validationErrorMessage),
                        containsString(firstnameErrorMessage),
                        containsString(passwordErrorMessage))))
                .andReturn()
                .getResolvedException();
        assertThat(exception).isInstanceOf(MethodArgumentNotValidException.class);
    }

    @Test
    public void testFailSignUpIfUserAlreadyExist() throws Exception {
        SignUpRequest invalidRequest = new SignUpRequest("admin", "Qwerty1!", "test", "test");
        String errorMessage = "User with username 'admin' already exist";

        Exception exception = mockMvc
                .perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(errorMessage)))
                .andReturn()
                .getResolvedException();
        assertThat(exception).isInstanceOf(ResourceAlreadyExistException.class);
    }

    @Test
    public void testSuccessSignUpIfDataValid() throws Exception {
        SignUpRequest validRequest = new SignUpRequest("test", "Qwerty1!", "test", "test");
        String expectedUsername = "\"username\":\"test\"";
        String expectedAuthority = "\"authority\":\"ROLE_USER\"";

        mockMvc
                .perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("api/users/3")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(allOf(
                        containsString(expectedUsername),
                        containsString(expectedAuthority))));
    }

    @Test
    public void testFailChangePasswordIfDataInvalid() throws Exception {
        String token = tokenHelper.generateToken("user");
        PasswordChanger passwordChanger = new PasswordChanger("", "Qwerty1!");
        String validationErrorMessage = "Validation failed";
        String oldPasswordErrorMessage = "Old password can't be blank";

        Exception exception = mockMvc
                .perform(post("/api/auth/change-password")
                        .with(csrf())
                        .header(jwtProperties.getHeader(), "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordChanger)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(allOf(
                        containsString(validationErrorMessage),
                        containsString(oldPasswordErrorMessage))))
                .andReturn()
                .getResolvedException();
        assertThat(exception).isInstanceOf(MethodArgumentNotValidException.class);
    }

    @Test
    public void testSuccessChangePasswordIfDataValid() throws Exception {
        String token = tokenHelper.generateToken("user");
        PasswordChanger passwordChanger = new PasswordChanger("Qwerty1!", "!1ytrewQ");

        mockMvc
                .perform(post("/api/auth/change-password")
                        .with(csrf())
                        .header(jwtProperties.getHeader(), "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordChanger)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testFailWhoAmIIfAccessDenied() throws Exception {
        String errorMessage = "Access is denied";

        Exception exception = mockMvc
                .perform(get("/api/auth/whoami"))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString(errorMessage)))
                .andReturn()
                .getResolvedException();
        assertThat(exception).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    public void testSuccessWhoAmIIfAccessGranted() throws Exception {
        String token = tokenHelper.generateToken("user");
        String expectedJson = "{"
                + "id: 1,"
                + "username: 'user',"
                + "firstname: 'Oliver',"
                + "lastname: 'Smith',"
                + "authorities: [{ id: 1, authority: 'ROLE_USER'}]"
                + "}";

        mockMvc
                .perform(get("/api/auth/whoami")
                        .header(jwtProperties.getHeader(), "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void testFailRefreshTokenIfUnauthorized() throws Exception {
        UserTokenState userTokenState = new UserTokenState();

        mockMvc
                .perform(get("/api/auth/refresh"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(userTokenState)));
    }

    @Test
    public void testSuccessRefreshTokenIfAuthorized() throws Exception {
        String token = tokenHelper.generateToken("user");

        mockMvc
                .perform(get("/api/auth/refresh")
                        .header(jwtProperties.getHeader(), "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(cookie().exists(jwtProperties.getCookie()))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
