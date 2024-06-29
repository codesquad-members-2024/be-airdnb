package codesquad.team05.domain.security.oauth2;

import codesquad.team05.domain.security.PrincipalUser;
import codesquad.team05.domain.user.User;
import codesquad.team05.domain.user.UserRepository;
import codesquad.team05.web.user.dto.request.OAuth2Attributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        //어느 OAuth 로그인인지 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributesName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();


        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuth2Attributes oAuth2Attributes = OAuth2Attributes.of(attributes, userNameAttributesName);

        User user = saveOrUpdate(oAuth2Attributes);

        return new PrincipalUser(user, attributes);
    }

    private User saveOrUpdate(OAuth2Attributes oAuth2Attributes) {

        return userRepository.findByLoginId(oAuth2Attributes.getEmail()).map(entity -> {
            entity.updateOAuth2User(oAuth2Attributes.getName());
            return entity;
        }).orElseGet(() -> {
            User newUser = oAuth2Attributes.toUserEntity();
            return userRepository.save(newUser);
        });
    }
}
