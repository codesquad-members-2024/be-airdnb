package team8.airbnb.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import team8.airbnb.oauth2.dto.CustomOAuth2User;
import team8.airbnb.jwt.jwtToken.JwtUtil;

@Component
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final JwtUtil jwtUtil;

  public CustomSuccessHandler(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

    String username = customUserDetails.getName();

    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
    GrantedAuthority auth = iterator.next();
    String role = auth.getAuthority();

    String token = jwtUtil.generateToken(username);
    logger.debug("token 생성완 : " + token);

    /*
    JWT 토큰을 Authorization 헤더에 추가
     */
    response.addHeader("Authorization", "Bearer " + token);
    response.sendRedirect("http://localhost:8080/success");
  }
}