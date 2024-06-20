package com.team01.airdnb.authorization.oauth2.user;

import lombok.Getter;

import java.util.Map;

@Getter
public class GoogleUserInfo {

    private final String socialId;
    private final String name;
    private final String email;
    private final String picture;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.socialId = String.valueOf(attributes.get("sub"));
        this.name = String.valueOf(attributes.get("name"));
        this.email = String.valueOf(attributes.get("email"));
        this.picture = String.valueOf(attributes.get("picture"));
    }
}
