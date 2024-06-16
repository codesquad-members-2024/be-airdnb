package team03.airdnb.authentication.oauth;

import lombok.Builder;
import team03.airdnb.user.User;

import java.util.Map;

@Builder
public record OAuthAttributes(
        String name,
        String email,
        String profileImg
) {

    public static OAuthAttributes of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) { // registration id별로 userInfo 생성
            case "google" -> ofGoogle(attributes);
            default -> ofGithub(attributes);
        };
    }

    private static OAuthAttributes ofGoogle(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .profileImg((String) attributes.get("picture"))
                .build();
    }

    private static OAuthAttributes ofGithub(Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("login"))
                .profileImg((String) attributes.get("avatar_url"))
                .build();
    }

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .profileImg(this.profileImg)
                .build();
    }
}
