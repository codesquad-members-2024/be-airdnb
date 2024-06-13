package com.team01.airdnb.image.dto;

import lombok.Builder;

@Builder
public record ImageListResponse(
    String imagePath
) {

}
