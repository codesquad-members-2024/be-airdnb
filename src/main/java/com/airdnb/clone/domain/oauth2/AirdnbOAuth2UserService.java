package com.airdnb.clone.domain.oauth2;

import com.airdnb.clone.domain.member.entity.Member;
import com.airdnb.clone.domain.member.repository.MemberRepository;
import java.util.Collections;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class AirdnbOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        AirdnbOAuth2Attribute attribute = AirdnbOAuth2Attribute.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        Member member = saveOrUpdate(attribute);

        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority(member.getRole())),
                attribute.getAttributes(),
                attribute.getNameAttributeKey()
                );
    }

    private Member saveOrUpdate(AirdnbOAuth2Attribute attribute) {
        Member member = memberRepository.findByLoginId(attribute.getName())
                .orElse(attribute.toMember());

        return memberRepository.save(member);
    }
}
