package com.airdnb.clone.domain.stay.controller.dto.response.edit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DescriptionEditResponse {

    private final String description;

    public static DescriptionEditResponse of(String description) {
        return new DescriptionEditResponse(description);
    }
}
