package codesquad.team05.domain.security.oauth2;

import codesquad.team05.domain.jwt.constants.JwtConstants;
import codesquad.team05.domain.jwt.service.JwtService;
import codesquad.team05.domain.security.PrincipalUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        PrincipalUser oauth2User = (PrincipalUser) authentication.getPrincipal();

        if(oauth2User.isGuest()){
            String loginId = oauth2User.getUser().getLoginId();

            String accessToken = jwtService.generateAccessToken(loginId);
            String refreshToken = jwtService.generateRefreshToken(loginId);

            response.setHeader(JwtConstants.ACCESS, JwtConstants.JWT_TYPE + accessToken);
            response.setHeader(JwtConstants.REFRESH, JwtConstants.JWT_TYPE + refreshToken);
        }
    }
}
