package com.airdnb.clone.domain.stay.controller.dto.request.edit;

import com.airdnb.clone.domain.common.Guest;
import com.airdnb.clone.domain.stay.entity.RoomInformation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomInfoEditRequest {

    @NotNull
    @Min(value = 1)
    private Integer guestCount;

    @NotNull
    @Min(value = 0)
    private Integer bedroomCount;

    @NotNull
    @Min(value = 1)
    private Integer bedCount;

    @NotNull
    @Min(value = 0)
    private Integer bathCount;

    public RoomInformation toRoomInformation() {
        return RoomInformation.builder()
                .guest(Guest.builder().guestCount(guestCount).build())
                .bedroomCount(bedroomCount)
                .bedCount(bedCount)
                .bathCount(bathCount)
                .build();
    }
}
