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
        return OAuthProvider.from(registrationId).extract(attributes);
    }

    public User toEntity() {
        return User.builder()
                .name(this.name)
                .profileImg(this.profileImg)
                .build();
    }
}
