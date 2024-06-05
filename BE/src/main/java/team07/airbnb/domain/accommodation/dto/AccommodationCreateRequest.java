package team07.airbnb.domain.accommodation.dto;

import team07.airbnb.domain.accommodation.property.AccommodationType;
import team07.airbnb.domain.accommodation.property.RoomInformation;
import team07.airbnb.domain.accommodation.property.AccommodationLocation;

import java.util.List;

public record AccommodationCreateRequest(
        AccommodationType type,
        RoomInformation roomInformation,
        AccommodationLocation address,
        String name,
        String description,
        List<String> images,
        int basePricePerDay
) {
}
