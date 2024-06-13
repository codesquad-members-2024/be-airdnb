package com.airdnb.clone.domain.stay.controller.dto.response.edit;

import com.airdnb.clone.domain.stay.entity.RoomInformation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RoomInfoResponse {

    private Integer guestCount;

    private Integer bedroomCount;

    private Integer bedCount;

    private Integer bathCount;

    public static RoomInfoResponse of(RoomInformation roomInfo) {
        return RoomInfoResponse.builder()
                .guestCount(roomInfo.getGuest().getGuestCount())
                .bedroomCount(roomInfo.getBedroomCount())
                .bedCount(roomInfo.getBedCount())
                .bathCount(roomInfo.getBathCount())
                .build();
    }
}
