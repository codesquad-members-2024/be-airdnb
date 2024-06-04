package team07.airbnb.domain.user.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team07.airbnb.domain.user.dto.SessionUser;
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.user.repository.UserRepository;
import team07.airbnb.domain.user.util.OAuthAttributes;
import java.util.Collections;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 로그인 진행 중인 서비스를 구분
        String registrationId = userRequest.getClientRegistration().getRegistrationId();


        // OAuth2 로그인 진행 시 키가 되는 필드 값
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute 등을 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // 사용자 저장 또는 업데이트
        UserEntity user = saveOrUpdate(attributes);

        // 세션에 사용자 정보 저장
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private UserEntity saveOrUpdate(OAuthAttributes attributes) {
        UserEntity user = userRepository.findByRegistrationIdAndName(attributes.getRegistrationId(), attributes.getName())
                // 가입된 사용자는 이름과 이미지, 사진만 업데이트
                .map(entity -> entity.update(attributes.getName(), attributes.getEmail(), attributes.getPicture()))
                // 가입되지 않은 사용자 => User 엔티티 생성
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}