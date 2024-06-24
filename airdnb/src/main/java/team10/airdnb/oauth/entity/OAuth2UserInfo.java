package team10.airdnb.oauth.entity;

import lombok.Builder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import team10.airdnb.error.ErrorCode;
import team10.airdnb.member.constant.MemberType;
import team10.airdnb.member.entity.Member;
import team10.airdnb.oauth.exception.AuthenticationException;

import java.util.Map;

@Builder
public record OAuth2UserInfo(
        String email,
        String name,
        String profile,
        MemberType memberType
) {

    private static final String KAKAO = "kakao";
    private static final String GITHUB = "github";
    private static final String GOOGLE = "google";

    private static final String ADDRESS = "@airdnb.site";

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case KAKAO -> ofKakao(attributes);
            case GOOGLE -> ofGoogle(attributes);
            case GITHUB -> ofGithub(attributes);
            default -> throw new AuthenticationException(ErrorCode.ILLEGAL_REGISTRATION_ID);
        };
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_acount");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        return OAuth2UserInfo.builder()
                .name((String) profile.get("nickname"))
                .email((String) account.get("email"))
                .profile((String) profile.get("profile_image_url"))
                .build();
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profile((String) attributes.get("picture"))
                .build();
    }

    private static OAuth2UserInfo ofGithub(Map<String, Object> attributes) {
        String name = (String) attributes.get("login");
        String email = String.format("%s%s", name, ADDRESS); // GitHub는 email이 제공되지 않아 따로 만들어줌

        return OAuth2UserInfo.builder()
                .name(name)
                .email(email)
                .profile((String) attributes.get("avatar_url"))
                .memberType(MemberType.GITHUB)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .id(email)
                .email(email)
                .memberName(name)
                .profile(profile)
                .memberType(memberType)
                .build();
    }
}
