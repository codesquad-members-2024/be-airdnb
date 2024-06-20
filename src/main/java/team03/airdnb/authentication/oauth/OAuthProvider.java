package team03.airdnb.authentication.oauth;

import java.util.Map;

public enum OAuthProvider {
    GOOGLE {
        @Override
        OAuthAttributes extract(Map<String, Object> attributes) {
            String name = (String) attributes.get("name");
            String email = (String) attributes.get("email");
            String profileImg = (String) attributes.get("picture");
            return new OAuthAttributes(name, email, profileImg);
        }
    },
    GITHUB {
        @Override
        OAuthAttributes extract(Map<String, Object> attributes) {
            String name = (String) attributes.get("login");
            String email = (String) attributes.get("email");
            String profileImg = (String) attributes.get("avatar_url");
            return new OAuthAttributes(name, email, profileImg);
        }
    };

    abstract OAuthAttributes extract(Map<String, Object> attributes);

    public static OAuthProvider from(String registrationId) {
        try {
            return OAuthProvider.valueOf(registrationId.toUpperCase());
        } catch (IllegalArgumentException e) {
            return GITHUB;
        }
    }
}
