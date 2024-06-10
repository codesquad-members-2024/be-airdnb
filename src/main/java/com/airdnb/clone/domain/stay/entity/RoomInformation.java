package com.airdnb.clone.domain.stay.entity;

import com.airdnb.clone.domain.common.Guest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class RoomInformation {

    @Builder.Default
    private Guest guest = Guest.builder().guestCount(1).build();

    @Builder.Default
    private Integer bedroomCount = 0;

    @Builder.Default
    private Integer bedCount = 1;

    @Builder.Default
    private Integer bathCount = 0;
}
