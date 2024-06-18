package com.airdnb.clone.domain.oauth2;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtClaimsBuilder {

    @Builder
    public static Map<String, Object> of(String id, String username, String profileImage, String authorities) {
        return Map.of("id", id, "username", username, "profileImage", profileImage, "authorities", authorities);
    }
}
