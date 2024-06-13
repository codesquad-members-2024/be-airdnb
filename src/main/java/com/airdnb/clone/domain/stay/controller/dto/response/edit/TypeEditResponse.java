package com.airdnb.clone.domain.stay.controller.dto.response.edit;

import com.airdnb.clone.domain.stay.entity.Stay.Type;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeEditResponse {

    private final Type type;

    public static TypeEditResponse of(Type type) {
        return new TypeEditResponse(type);
    }
}
