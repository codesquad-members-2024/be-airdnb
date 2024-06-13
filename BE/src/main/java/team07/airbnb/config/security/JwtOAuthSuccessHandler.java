package team07.airbnb.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team07.airbnb.common.auth.jwt.JwtUserDetails;
import team07.airbnb.data.user.dto.response.TokenUserInfo;

import java.io.IOException;
import java.io.PrintWriter;
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
        TokenUserInfo user = userDetails.getUser();

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> responseMap = new HashMap<>();

        responseMap.put("token", userDetails.getPassword());
        responseMap.put("userId", user.id().toString());
        responseMap.put("userName", user.name());
        responseMap.put("profileImage", user.profileImg());

        PrintWriter writer = response.getWriter();
        writer.print(mapper.writeValueAsString(responseMap));
        writer.flush();
    }
}
