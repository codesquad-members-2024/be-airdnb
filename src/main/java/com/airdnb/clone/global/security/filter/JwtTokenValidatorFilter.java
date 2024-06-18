package com.airdnb.clone.global.security.filter;

import static com.airdnb.clone.global.security.constants.SecurityConstants.JWT_AUTHORIZATION_HEADER;
import static com.airdnb.clone.global.security.constants.SecurityConstants.JWT_REFRESH_TOKEN_HEADER;

import com.airdnb.clone.domain.oauth2.JwtTokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("===jwt validator filter start===");
        String jwt = request.getHeader(JWT_AUTHORIZATION_HEADER);

        if (jwt == null) {
            return;
        }

        try {
            Claims claims = jwtTokenService.getClaims(jwt);

            String username = String.valueOf(claims.get("username"));
            String authorities = String.valueOf(claims.get("authorities"));

            // 인증 토큰에 credentials(비밀번호) 를 제외한 나머지 값들 채워넣기 -> 시큐리티 컨텍스트가 정상 인증으로 인식하게 함
            Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (ExpiredJwtException e) {
            String refreshToken = request.getHeader(JWT_REFRESH_TOKEN_HEADER);

            if (refreshToken == null) {
                throw new BadCredentialsException("Not Found Refresh Token");
            }

            try {
                // 새로운 JWT 발급
                Claims claims = jwtTokenService.getClaims(refreshToken);

                String newJwt = jwtTokenService.issueAccessToken(claims);
                String refreshedToken = jwtTokenService.issueRefreshToken(claims);

                response.setHeader(JWT_AUTHORIZATION_HEADER, newJwt);
                response.setHeader(JWT_REFRESH_TOKEN_HEADER, refreshedToken);

                String username = String.valueOf(claims.get("username"));
                String authorities = String.valueOf(claims.get("authorities"));

                Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (Exception ex) {
                throw new BadCredentialsException("Invalid Refresh Token received");
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid JWT received");
        }

        filterChain.doFilter(request, response);
        log.info("===jwt validator filter end===");
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("http://localhost:5173/login");
    }
}
