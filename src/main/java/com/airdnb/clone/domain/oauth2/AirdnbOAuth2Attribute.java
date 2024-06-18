package com.airdnb.clone.domain.oauth2;

import com.airdnb.clone.domain.member.entity.Member;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
public class AirdnbOAuth2Attribute {

    private final String registrationId;
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String picture;

    protected static AirdnbOAuth2Attribute of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver(registrationId, "id", attributes);
        }

        if ("github".equals(registrationId)) {
            return ofGithub(registrationId, "id", attributes);
        }

        return ofGoogle(registrationId, userNameAttributeName, attributes);
    }

    private static AirdnbOAuth2Attribute ofGoogle(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return AirdnbOAuth2Attribute.builder()
                .registrationId(registrationId)
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static AirdnbOAuth2Attribute ofGithub(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        return AirdnbOAuth2Attribute.builder()
                .registrationId(registrationId)
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("avatar_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    // TODO: naver 연동 추가 예정
    private static AirdnbOAuth2Attribute ofNaver(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return AirdnbOAuth2Attribute.builder()
                .registrationId(registrationId)
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    // 처음 가입 시
    public Member toMember() {
        return Member.builder()
                .loginId(attributes.get(nameAttributeKey) + "@" + registrationId)
                .name(name)
                .role("OAUTH2_USER")
                .build();
    }
}
