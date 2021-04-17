package com.bfwg.rest;

import com.bfwg.config.JwtProperties;
import com.bfwg.security.TokenHelper;
import com.bfwg.service.impl.UserDetailsLoaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicController.class)
public class PublicControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsLoaderService userDetailsLoaderService;

    @MockBean
    private TokenHelper tokenHelper;

    @MockBean
    private JwtProperties jwtProperties;

    @Test
    public void testGetFoo() throws Exception {
        mockMvc
                .perform(get("/api/foo"))
                .andExpect(status().isOk())
                .andExpect(content().json("{foo: bar}"));
    }
}
