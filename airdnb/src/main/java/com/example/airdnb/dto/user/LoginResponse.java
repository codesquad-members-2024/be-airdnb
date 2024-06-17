package com.example.airdnb.dto.user;

import com.example.airdnb.domain.user.User;

public record LoginResponse(
    String email,
    String name,
    String token
) {

    public static LoginResponse from(User user, String token) {
        return new LoginResponse(user.getEmail(), user.getName(), token);
    }

}
