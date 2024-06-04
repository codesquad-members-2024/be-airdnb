package team07.airbnb.domain.user.util;

import lombok.Builder;
import lombok.Getter;
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.user.enums.RegistrationID;
import team07.airbnb.domain.user.enums.Role;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;
    private String registrationId;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes,
                           String nameAttributeKey, String name,
                           String email, String picture, String registrationId) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.registrationId = registrationId;
    }

    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {

        OAuthAttributes result = null;

        switch (RegistrationID.valueOf(registrationId.toUpperCase())) {
            case GOOGLE -> result = ofGoogle(registrationId, userNameAttributeName, attributes);
            case GITHUB -> result = ofGithub(registrationId, userNameAttributeName, attributes);
        }

        return result;
    }

    private static OAuthAttributes ofGithub(String registrationId, String usernameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("avatar_url"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .registrationId(registrationId)
                .build();
    }

    private static OAuthAttributes ofGoogle(String registrationId, String usernameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .registrationId(registrationId)
                .build();
    }
    public UserEntity toEntity() {
        return UserEntity.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .registrationId(registrationId)
                .build();
    }

}