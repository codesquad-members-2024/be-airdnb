package com.airdnb.clone.global.security.filter;

import static com.airdnb.clone.global.security.constants.SecurityConstants.AUTHORITIES;
import static com.airdnb.clone.global.security.constants.SecurityConstants.JWT_AUTHORIZATION_HEADER;
import static com.airdnb.clone.global.security.constants.SecurityConstants.JWT_REFRESH_TOKEN_HEADER;
import static com.airdnb.clone.global.security.constants.SecurityConstants.USERNAME;

import com.airdnb.clone.domain.oauth2.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("===jwt generator filter start===");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) { // 인증 정보가 없으면 조기 종료
            return;
        }

        Map<String, Object> claims = Map.of(USERNAME, authentication.getName(), AUTHORITIES,
                jwtTokenService.populateAuthorities(authentication.getAuthorities()));

        String jwt = jwtTokenService.issueAccessToken(claims);
        String refreshToken = jwtTokenService.issueRefreshToken(claims);

        response.addHeader(JWT_AUTHORIZATION_HEADER, jwt); // 응답 헤더에 추가
        response.addHeader(JWT_REFRESH_TOKEN_HEADER, refreshToken); // 리프레시 추가

        filterChain.doFilter(request, response);
        log.info("jwt generator filter end");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("http://localhost:5173/login"); // /members 경로가 아니면(true) 해당 필터가 실행
    }
}
