package team07.airbnb.domain.auth;

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
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.util.jwt.JwtUtil;

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
        if (jwt != null && jwtUtil.validateToken(jwt)) {
            UserEntity userEntity = jwtUtil.getUserEntity(jwt);
            Authentication authentication = new JwtAuthentication(jwt, userEntity, true);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
