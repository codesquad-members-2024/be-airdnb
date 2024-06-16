package team03.airdnb.authentication.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team03.airdnb.user.User;
import team03.airdnb.user.UserRepository;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, oAuth2UserAttributes);
        saveOrUpdate(attributes);

        return new DefaultOAuth2User(
                Collections.singleton(new OAuth2UserAuthority(oAuth2UserAttributes)),
                oAuth2UserAttributes,
                userNameAttributeName);
    }

    private void saveOrUpdate(OAuthAttributes attributes) {
        User user = userRepository.findByName(attributes.name()).orElseGet(attributes::toEntity);
        userRepository.save(user);
    }
}