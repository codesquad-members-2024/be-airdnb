package com.team01.airdnb.user.dto;

import lombok.Builder;

@Builder
public record UserCommentResponse(
    String username
) {
}
