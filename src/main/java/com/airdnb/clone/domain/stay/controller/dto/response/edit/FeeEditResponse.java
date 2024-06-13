package com.airdnb.clone.domain.stay.controller.dto.response.edit;

import com.airdnb.clone.domain.stay.entity.StayFee;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FeeEditResponse {

    private final StayFee fee;

    public static FeeEditResponse of(StayFee fee) {
        return new FeeEditResponse(fee);
    }
}
