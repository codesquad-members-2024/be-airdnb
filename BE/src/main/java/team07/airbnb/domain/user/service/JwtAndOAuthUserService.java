package team07.airbnb.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team07.airbnb.domain.auth.JwtAuthentication;
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.user.repository.UserRepository;
import team07.airbnb.domain.auth.JwtUserDetails;
import team07.airbnb.domain.auth.OAuthAttributes;
import team07.airbnb.util.jwt.JwtUtil;

@RequiredArgsConstructor
@Service
@Slf4j
public class JwtAndOAuthUserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

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

        JwtUserDetails userDetails = new JwtUserDetails(user, attributes.getAttributes());

        String jwt = null;
        try {
            jwt = jwtUtil.generateToken(userDetails);
        } catch (JsonProcessingException e) {
            throw new OAuth2AuthenticationException("유저 정보 JSON 변환 실패");
        }
        log.info("create jwt : " + jwt);

        userDetails.setPassword(jwt);

        JwtAuthentication authentication = new JwtAuthentication(jwt, user, true);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return userDetails;
    }


    private UserEntity saveOrUpdate(OAuthAttributes attributes) {
        UserEntity user = userRepository.findByRegistrationIdAndName(attributes.getRegistrationId(), attributes.getName())
                // 가입된 사용자는 이름과 이미지, 사진만 업데이트
                .map(entity -> entity.updateInfo(attributes.getName(), attributes.getEmail(), attributes.getPicture()))
                // 가입되지 않은 사용자 => User 엔티티 생성
                .orElse(attributes.toEntity());

        return userRepository.save(user);
    }
}