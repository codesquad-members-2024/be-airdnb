package team07.airbnb.common.auth.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import team07.airbnb.common.auth.jwt.JwtAuthenticationFilter;
import team07.airbnb.common.auth.exception.AuthenticateException;
import team07.airbnb.common.auth.exception.UnAuthorizedException;
import team07.airbnb.domain.user.enums.Role;

import java.util.Collection;

import static team07.airbnb.domain.user.enums.Role.*;

@Component
@Aspect
@RequiredArgsConstructor
public class AuthenticationAspect {

    private final JwtAuthenticationFilter jwtRequestFilter;
    private final HttpServletRequest request;

    @Around("@annotation(authenticated)")
    public Object authenticate(ProceedingJoinPoint joinPoint, Authenticated authenticated) throws Throwable {
        jwtRequestFilter.validateJwt(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticateException("인증과정에서 문제가 발생했습니다");
        }

        if (!userHasGrant(authenticated, authentication)) {
            throw new UnAuthorizedException();
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
