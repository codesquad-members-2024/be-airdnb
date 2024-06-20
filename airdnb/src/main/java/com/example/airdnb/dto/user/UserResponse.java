package com.example.airdnb.dto.user;

import com.example.airdnb.domain.user.User;
import com.example.airdnb.domain.user.User.Role;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public record UserResponse(
    Long id,
    String email,
    String name,
    Role role) {

    public static UserResponse fromUser(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getRole());
    }
}
