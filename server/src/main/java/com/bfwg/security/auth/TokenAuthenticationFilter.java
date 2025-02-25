package com.bfwg.security.auth;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bfwg.security.TokenHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenHelper tokenHelper;
    private final UserDetailsService userDetailsService;

    /*
     * The below paths will get ignored by the filter
     */
    public static final String ROOT_MATCHER = "/";
    public static final String FAVICON_MATCHER = "/favicon.ico";
    public static final String HTML_MATCHER = "/**/*.html";
    public static final String CSS_MATCHER = "/**/*.css";
    public static final String JS_MATCHER = "/**/*.js";
    public static final String IMG_MATCHER = "/images/*";
    public static final String LOGIN_MATCHER = "/auth/login";
    public static final String LOGOUT_MATCHER = "/auth/logout";

    private final List<String> pathsToSkip = Arrays.asList(
            ROOT_MATCHER,
            HTML_MATCHER,
            FAVICON_MATCHER,
            CSS_MATCHER,
            JS_MATCHER,
            IMG_MATCHER,
            LOGIN_MATCHER,
            LOGOUT_MATCHER
    );

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        String authToken = tokenHelper.getToken(request);
        if (authToken != null && !skipPathRequest(request, pathsToSkip)) {
            Authentication authentication = getAuthenticationFromToken(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            SecurityContextHolder.getContext().setAuthentication(new AnonAuthentication());
        }

        chain.doFilter(request, response);
    }

    private Authentication getAuthenticationFromToken(String authToken) {
        try {
            String username = tokenHelper.getUsername(authToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return new TokenBasedAuthentication(userDetails, authToken);
        } catch (Exception e) {
            return new AnonAuthentication();
        }
    }

    private boolean skipPathRequest(HttpServletRequest request, List<String> pathsToSkip) {
        List<RequestMatcher> m = pathsToSkip.stream()
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toList());
        OrRequestMatcher matchers = new OrRequestMatcher(m);
        return matchers.matches(request);
    }
}
