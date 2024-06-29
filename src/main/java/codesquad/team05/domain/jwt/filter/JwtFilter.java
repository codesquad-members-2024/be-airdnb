package codesquad.team05.domain.jwt.filter;

import codesquad.team05.domain.jwt.constants.JwtConstants;
import codesquad.team05.domain.jwt.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String[] whitelist = {"/", "/index.html", "/login", "/signUp", "/renew",
            "/login/oauth2/code/**", "/oauth2/**", "/error"};

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(JwtConstants.JWT_HEADER);

        if (header == null || !header.startsWith(JwtConstants.JWT_TYPE)) {
           sendErrorResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Token이 존재하지 않습니다.");
           return;
        }

        try {
            String token = JwtUtils.getTokenFromHeader(header);
            Claims claims = JwtUtils.validateJwtToken(token);

            UsernamePasswordAuthenticationToken authenticationToken = JwtUtils.getAuthenticationToken(claims);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        } catch (Exception e){
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token이 유효하지 않습니다.");
            
        }
        filterChain.doFilter(request, response);

    }

    private void sendErrorResponse(HttpServletResponse response, int statusCode, String message) throws IOException {
        response.setStatus(statusCode);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        new ObjectMapper().writeValue(response.getWriter(), message);
    }
}
