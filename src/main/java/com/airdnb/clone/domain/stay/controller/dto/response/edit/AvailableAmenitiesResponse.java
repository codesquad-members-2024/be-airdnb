package com.airdnb.clone.domain.stay.controller.dto.response.edit;

import com.airdnb.clone.domain.stay.entity.Amenity;
import com.airdnb.clone.domain.stay.entity.AvailableAmenity;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AvailableAmenitiesResponse {

    private final List<Long> amenities;

    public static AvailableAmenitiesResponse of(List<AvailableAmenity> amenities) {
        return new AvailableAmenitiesResponse(extractAmenityId(amenities));
    }

    private static List<Long> extractAmenityId(List<AvailableAmenity> amenities) {
        return amenities.stream()
                .map(AvailableAmenity::getAmenity)
                .map(Amenity::getId)
                .toList();
    }
}
