package team07.airbnb.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import team07.airbnb.domain.auth.JwtUserDetails;
import team07.airbnb.domain.user.entity.UserEntity;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtOAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        UserEntity user = userDetails.getUser();

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> responseMap = new HashMap<>();

        responseMap.put("token", userDetails.getPassword());
        responseMap.put("userId", user.getId().toString());
        responseMap.put("userName", user.getName());

        PrintWriter writer = response.getWriter();
        writer.print(mapper.writeValueAsString(responseMap));
        writer.flush();
    }
}
