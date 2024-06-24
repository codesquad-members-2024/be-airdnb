package team10.airdnb.oauth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import team10.airdnb.jwt.dto.JwtTokenDto;
import team10.airdnb.jwt.service.TokenManager;
import team10.airdnb.member.entity.Member;
import team10.airdnb.member.exception.MemberIdNotFoundException;
import team10.airdnb.member.repository.MemberRepository;
import team10.airdnb.oauth.entity.OAuth2UserInfo;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenManager tokenManager;
    private final MemberRepository memberRepository;
    private static final String URI = "https://airdnb.site/";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken authenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = authenticationToken.getPrincipal();
        String registrationId = authenticationToken.getAuthorizedClientRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, attributes);
        String email = oAuth2UserInfo.email();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberIdNotFoundException::new);

        JwtTokenDto jwtTokenDto = tokenManager.createJwtTokenDto(member);

        String redirectUrl = UriComponentsBuilder.fromUriString(URI)
                .queryParam("accessToken", jwtTokenDto.getAccessToken())
                .queryParam("refreshToken", jwtTokenDto.getRefreshToken())
                .build().toString();

        response.sendRedirect(redirectUrl);
    }
}
