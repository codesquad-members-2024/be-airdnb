package com.airdnb.clone.domain.oauth2;

import static com.airdnb.clone.global.security.constants.SecurityConstants.COOKIE_EXPIRATION_TIME;
import static com.airdnb.clone.global.security.constants.SecurityConstants.JWT_ACCESS_COOKIE_KEY;
import static com.airdnb.clone.global.security.constants.SecurityConstants.JWT_REFRESH_COOKIE_KEY;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AirdnbOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenService jwtTokenService;

    @Value("#{environment['allowOrigin']}")
    private String allowOrigin;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Map<String, Object> claims = createClaims(authentication);

        String jwt = jwtTokenService.issueAccessToken(claims);
        String refreshToken = jwtTokenService.issueRefreshToken(claims);

        Cookie jwtCookie = createJwtCookie(JWT_ACCESS_COOKIE_KEY, jwt);
        Cookie refreshCookie = createJwtCookie(JWT_REFRESH_COOKIE_KEY, refreshToken);

        response.addCookie(jwtCookie);
        response.addCookie(refreshCookie);

        response.sendRedirect(allowOrigin);
    }

    private Map<String, Object> createClaims(Authentication authentication) {
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        Map<String, Object> oAuth2UserAttributes = oAuth2User.getAttributes();
        String id = oAuth2User.getName(); // int 타입 고유 아이디
        String username = "";
        String profileImage = "";

        // 프로필 이미지 분기
        String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        if (registrationId.equalsIgnoreCase("google")) {
            profileImage = oAuth2UserAttributes.get("picture").toString();
            username = oAuth2UserAttributes.get("name").toString();
        }
        if (registrationId.equalsIgnoreCase("github")) {
            profileImage = oAuth2UserAttributes.get("avatar_url").toString();
            username = oAuth2UserAttributes.get("name").toString();
        }
        if (registrationId.equalsIgnoreCase("kakao")) {
            profileImage = oAuth2UserAttributes.get("profile_image").toString();
            username = oAuth2UserAttributes.get("nickname").toString();
        }

        String authorities = jwtTokenService.populateAuthorities(authentication.getAuthorities());

        return JwtClaimsBuilder.builder()
                .id(id)
                .profileImage(profileImage)
                .username(username)
                .authorities(authorities)
                .build();
    }

    private Cookie createJwtCookie(String cookieKey, String jwt) {
        Cookie cookie = new Cookie(cookieKey, jwt);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setHttpOnly(false); // FIXME: 개발 중에만 사용
        cookie.setMaxAge(COOKIE_EXPIRATION_TIME);

        return cookie;
    }
}
