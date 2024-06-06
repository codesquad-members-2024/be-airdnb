package team07.airbnb.domain.accommodation.dto;

import team07.airbnb.domain.accommodation.entity.AccommodationEntity;
import team07.airbnb.domain.accommodation.property.AccommodationLocation;
import team07.airbnb.domain.accommodation.property.AccommodationType;

public record AccommodationListResponse(
        long id,
        String name,
        String imageUrl,
        AccommodationLocation location,
        AccommodationType type
) {

    public static AccommodationListResponse of(AccommodationEntity accomodation) {
        return new AccommodationListResponse(
                accomodation.getId(),
                accomodation.getName(),
                !accomodation.getPictures().isEmpty() ? accomodation.getPictures().get(0).getUrl() : null,
                accomodation.getAddress(),
                accomodation.getType()
        );
    }
}
