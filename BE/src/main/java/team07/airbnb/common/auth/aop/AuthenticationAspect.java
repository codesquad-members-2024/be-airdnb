package team07.airbnb.common.auth.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import team07.airbnb.common.auth.jwt.JwtAuthenticationFilter;
import team07.airbnb.data.user.dto.response.TokenUserInfo;
import team07.airbnb.data.user.enums.Role;
import team07.airbnb.exception.auth.AuthenticateException;
import team07.airbnb.exception.auth.UnAuthorizedException;

import java.util.Collection;

import static team07.airbnb.data.user.enums.Role.ADMIN;
import static team07.airbnb.data.user.enums.Role.HOST;

@Component
@Aspect
@RequiredArgsConstructor
public class AuthenticationAspect {

    private final JwtAuthenticationFilter jwtRequestFilter;
    private final HttpServletRequest request;

    @Around("@annotation(authenticated)")
    public Object authenticate(ProceedingJoinPoint joinPoint, Authenticated authenticated) throws Throwable {
        String jwt = jwtRequestFilter.validateJwt(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticateException("인증과정에서 문제가 발생했습니다", jwt);
        }

        if (!userHasGrant(authenticated, authentication)) {
            TokenUserInfo user = (TokenUserInfo) authentication.getPrincipal();
            throw new UnAuthorizedException("사용자 {%d} 가 권한이 없는 서비스에 접근함. 필요권한 -> {%S}, 소유 권한 -> {%s}"
                    .formatted(user.id(), authenticated.value().getKey(), user.stringRole()));
        }
        
        return joinPoint.proceed();
    }

    private boolean userHasGrant(Authenticated authenticated, Authentication authentication) {
        Role grantRole = authenticated.value();
        boolean hasRole = true;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        switch (grantRole) {
            case ADMIN -> {
                hasRole = authorities.contains(new SimpleGrantedAuthority(ADMIN.getKey()));
            }
            case HOST -> {
                hasRole = authorities.contains(new SimpleGrantedAuthority(ADMIN.getKey())) ||
                        authorities.contains(new SimpleGrantedAuthority(HOST.getKey()));
            }
        }
        return hasRole;
    }
}
