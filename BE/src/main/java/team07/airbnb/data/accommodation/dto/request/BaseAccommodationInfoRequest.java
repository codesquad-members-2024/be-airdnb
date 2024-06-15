package team07.airbnb.data.accommodation.dto.request;

import team07.airbnb.data.accommodation.enums.AccommodationType;
import team07.airbnb.entity.embed.AccommodationLocation;

public record BaseAccommodationInfoRequest(
        AccommodationType type,
        AccommodationLocation address,
        int basePricePerDay
) {
}

