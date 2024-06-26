package com.team01.airdnb.authorization.security.handler;

import com.team01.airdnb.authorization.jwt.utils.JwtConstants;
import com.team01.airdnb.authorization.jwt.utils.JwtUtils;
import com.team01.airdnb.user.PrincipalDetail;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class CommonLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("--------------------------- CommonLoginSuccessHandler ---------------------------");

        PrincipalDetail principal = (PrincipalDetail) authentication.getPrincipal();

        log.info("authentication.getPrincipal() = {}", principal);

        Map<String, Object> responseMap = principal.getMemberInfo();
        String jwt = JwtUtils.generateToken(responseMap, JwtConstants.ACCESS_EXP_TIME);

        Cookie accessTokenCookie = new Cookie("jwt", jwt);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge((int) JwtConstants.ACCESS_EXP_TIME);

        response.addCookie(accessTokenCookie);

        log.info("Login successful for user: {}", principal.getUsername());
        log.debug("Response map: {}", responseMap);

        response.setContentType("application/json; charset=UTF-8");
        response.sendRedirect("http://localhost:8077/callback");
    }
}