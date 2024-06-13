package com.airdnb.clone.domain.stay.controller.dto.response;

import com.airdnb.clone.domain.stay.controller.dto.response.edit.RoomInfoResponse;
import com.airdnb.clone.domain.stay.entity.Amenity;
import com.airdnb.clone.domain.stay.entity.AvailableAmenity;
import com.airdnb.clone.domain.stay.entity.Stay;
import com.airdnb.clone.domain.stay.entity.Stay.Status;
import com.airdnb.clone.domain.stay.entity.Stay.Type;
import com.airdnb.clone.domain.stay.entity.StayFee;
import com.airdnb.clone.domain.stay.entity.StayImage;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class StayDetailResponse {

    private final Long id;
    private final String alias;
    private final String location;
    private final LocalTime checkInTime;
    private final LocalTime checkOutTime;
    private final String description;
    private final StayFee fee;
    private final Type type;
    private final Status status;
    private List<Long> amenityIds;
    private List<StayImage> images;
    private final RoomInfoResponse roomInfo;

    public static StayDetailResponse of(Stay stay) {
        return new StayDetailResponse(
                stay.getId(),
                stay.getAlias(),
                stay.getLocation(),
                stay.getCheckInTime(),
                stay.getCheckOutTime(),
                stay.getDescription(),
                stay.getFee(),
                stay.getType(),
                stay.getStatus(),
                extractAmenityId(stay.getAmenities()),
                stay.getImages(),
                RoomInfoResponse.of(stay.getRoomInfo())
        );
    }

    private static List<Long> extractAmenityId(List<AvailableAmenity> amenities) {
        return amenities.stream()
                .map(AvailableAmenity::getAmenity)
                .map(Amenity::getId)
                .toList();
    }
}
