package com.team01.airdnb.authorization.oauth2.service;

import com.team01.airdnb.user.PrincipalDetail;
import com.team01.airdnb.authorization.oauth2.user.GoogleUserInfo;
import com.team01.airdnb.user.Role;
import com.team01.airdnb.user.SocialType;
import com.team01.airdnb.user.User;
import com.team01.airdnb.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("--------------------------- OAuth2UserService ---------------------------");

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        log.info("OAuth2USer = {}", oAuth2User);
        log.info("attributes = {}", attributes);

        // nameAttributeKey
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        log.info("nameAttributeKey = {}", userNameAttributeName);

        GoogleUserInfo googleUserInfo = new GoogleUserInfo(attributes);
        String socialId = googleUserInfo.getSocialId();
        String name = googleUserInfo.getName();
        String email = googleUserInfo.getEmail();
        String profile = googleUserInfo.getPicture();

        // 소셜 ID 로 사용자를 조회, 없으면 socialId 와 이름으로 사용자 생성
        Optional<User> bySocialId = userRepository.findBySocialId(socialId);
        User user = bySocialId.orElseGet(() -> saveSocialMember(socialId, name, email, profile, SocialType.GOOGLE));

        return new PrincipalDetail(user, Collections.singleton(new SimpleGrantedAuthority(user.getRole().getValue())),
                attributes);
    }

    // 소셜 ID 로 가입된 사용자가 없으면 새로운 사용자를 만들어 저장한다
    public User saveSocialMember(String socialId, String name, String email, String profile, SocialType socialType) {
        log.info("--------------------------- saveSocialMember ---------------------------");
        User newMember = User.builder().socialId(socialId).username(name).role(Role.USER).email(email).profile(profile).socialType(socialType).build();
        return userRepository.save(newMember);
    }
}
