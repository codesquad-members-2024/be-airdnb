package com.example.airdnb.config;

import com.example.airdnb.service.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final static String AUTHORIZATION_HEADER = "Authorization";
    private final static String BEARER_AUTH = "Bearer ";

    private final TokenProvider tokenProvider;
    private final UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = getAccessToken(request.getHeader(AUTHORIZATION_HEADER));
        log.info("token = {}", token);
        if (tokenProvider.validateToken(token)) {
            String username = tokenProvider.getUsername(token);
            UserDetails userDetails = userDetailService.loadUserByUsername(username);
            Authentication authentication = tokenProvider.getAuthentication(token, userDetails);
            log.info("authentication = {}", authentication.toString());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String header) {
        if (header != null && header.startsWith(BEARER_AUTH)) {
            return header.substring(7);
        }
        return null;
    }
}
