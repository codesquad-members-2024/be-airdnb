package com.airdnb.clone.domain.stay.controller.dto.response.edit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LocationEditResponse {

    private final String location;

    public static LocationEditResponse of(String location) {
        return new LocationEditResponse(location);
    }
}
