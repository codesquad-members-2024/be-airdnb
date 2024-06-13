package com.airdnb.clone.domain.stay.controller.dto.response.edit;

import com.airdnb.clone.domain.stay.entity.Stay.Status;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StatusEditResponse {

    private final Status status;

    public static StatusEditResponse of(Status status) {
        return new StatusEditResponse(status);
    }
}
