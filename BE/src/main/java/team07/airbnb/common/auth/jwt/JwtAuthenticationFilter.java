package team07.airbnb.common.auth.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import team07.airbnb.domain.user.dto.TokenUserInfo;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        validateJwt(request);
        filterChain.doFilter(request, response);
    }

    public void validateJwt(HttpServletRequest request) throws JsonProcessingException {
        String jwt = resolveToken(request);
        Authentication authentication = null;
        TokenUserInfo userInfo = null;

        if (jwt != null || !jwtUtil.validateToken(jwt)) {
            authentication = new JwtAuthentication(jwt, userInfo, false);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return;
        }

        userInfo = jwtUtil.getTokenUserInfo(jwt);
        authentication = new JwtAuthentication(jwt, userInfo, true);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
