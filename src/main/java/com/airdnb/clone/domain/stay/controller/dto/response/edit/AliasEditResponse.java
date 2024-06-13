package com.airdnb.clone.domain.stay.controller.dto.response.edit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AliasEditResponse {

    private final String alias;

    public static AliasEditResponse of(String alias) {
        return new AliasEditResponse(alias);
    }
}
