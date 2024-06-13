package com.team01.airdnb.user.dto;

import lombok.Builder;

@Builder
public record UserHostResponse(
    String username,
    Double score
) {
}
