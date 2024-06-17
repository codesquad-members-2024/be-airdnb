package team10.airdnb.accommodation.controller.response;

import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation_amenity.dto.AccommodationAmenityDto;

public record AccommodationCreateResponse(
        Accommodation accommodation,
        AccommodationAmenityDto amenities
) {
    public static AccommodationCreateResponse from(Accommodation accommodation, AccommodationAmenityDto amenities) {
        return new AccommodationCreateResponse(
                accommodation,
                amenities
        );
    }
}
