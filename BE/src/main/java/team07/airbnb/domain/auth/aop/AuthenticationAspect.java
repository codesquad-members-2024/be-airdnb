package team07.airbnb.domain.auth.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import team07.airbnb.domain.auth.JwtAuthenticationFilter;

@Component
@Aspect
@RequiredArgsConstructor
public class AuthenticationAspect {

    private final JwtAuthenticationFilter jwtRequestFilter;
    private final HttpServletRequest request;

    @Around("@annotation(Authenticated)")
    public Object authenticate(ProceedingJoinPoint joinPoint) throws Throwable {
        jwtRequestFilter.validateJwt(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User not authenticated");
        }
        return joinPoint.proceed();
    }
}
