package com.team01.airdnb.user.dto;

import com.team01.airdnb.user.Role;
import com.team01.airdnb.user.SocialType;
import lombok.Builder;

@Builder
public record UserShowResponse(
    Long id,
    String username,
    String email,
    String profile,
    Role role,
    SocialType socialType,
    String socialId
) {

}
