package codesquad.team05.web.user.dto.request;

import codesquad.team05.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class OAuth2Attributes {


    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuth2Attributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuth2Attributes of(Map<String, Object> attributes, String nameAttributeKey) {
      return ofGoogle(nameAttributeKey, attributes);
    }


    private static OAuth2Attributes ofGoogle(String usernameAttributeName,
                                            Map<String, Object> attributes){
        return OAuth2Attributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(usernameAttributeName)
                .build();
    }

    public User toUserEntity(){

        User user = new User(
                this.email,
                this.getName(),
                UUID.randomUUID().toString(),
                null,
                null
        );

        user.setRoleAsGuest();

        return user;
    }
}
