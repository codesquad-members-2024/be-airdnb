package team07.airbnb.domain.accommodation.dto;

import team07.airbnb.domain.accommodation.property.RoomInformation;
import team07.airbnb.domain.accommodation.property.AccommodationLocation;
import team07.airbnb.domain.accommodation.property.AccomodationType;

import java.util.List;

public record AccomodationCreateRequest(
        AccomodationType type,
        RoomInformation roomInformation,
        AccommodationLocation address,
        String name,
        String description,
        List<String> images,
        int basePricePerDay
) {
}
