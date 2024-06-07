package com.airdnb.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final MemberSecurityService memberSecurityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String token = jwtUtil.getToken(request);
        if (token != null && jwtUtil.validateToken(token)) {
            log.info("토큰 검증 완료");
            String id = jwtUtil.getId(token);
            SecurityMember securityMember = memberSecurityService.loadUserByUsername(id);
            if (securityMember != null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityMember, null,
                    securityMember.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Security Context에 '{}' 인증 정보를 저장했습니다.", id);
            }
        }
        log.info("토큰 검증 실패");

        filterChain.doFilter(request, response);
    }
}
