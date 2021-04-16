package com.bfwg.config;

import com.bfwg.security.TokenHelper;
import com.bfwg.security.auth.TokenAuthenticationFilter;
import com.bfwg.security.handler.AuthenticationSuccessHandler;
import com.bfwg.security.handler.RestAuthenticationEntryPoint;
import com.bfwg.security.handler.RestAuthenticationFailureHandler;
import com.bfwg.service.impl.UserDetailsLoaderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsLoaderService jwtUserDetailsService;
    private final TokenHelper tokenHelper;
    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(jwtUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationSuccessHandler authenticationSuccessHandler =
                new AuthenticationSuccessHandler(tokenHelper, objectMapper, jwtProperties);
        RestAuthenticationFailureHandler authenticationFailureHandler =
                new RestAuthenticationFailureHandler(objectMapper);
        RestAuthenticationEntryPoint restAuthenticationEntryPoint =
                new RestAuthenticationEntryPoint(objectMapper);

        // @formatter:off
        http
                .csrf()
                        .ignoringAntMatchers("/api/auth/login", "/api/auth/signup")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                        .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                        .anyRequest()
                        .authenticated()
                .and()
                .formLogin()
                        .loginPage("/api/auth/login")
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler)
                .and()
                .logout()
                        .logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/logout"))
                        .deleteCookies(jwtProperties.getCookie())
                .and()
                .addFilterBefore(
                        new TokenAuthenticationFilter(tokenHelper, jwtUserDetailsService),
                        BasicAuthenticationFilter.class);
        // @formatter:on
    }
}
