package team07.airbnb.data.accommodation.dto.response;

import team07.airbnb.data.accommodation.enums.AccommodationType;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.entity.embed.AccommodationLocation;

public record AccommodationListResponse(
        long id,
        String name,
        String imageUrl,
        AccommodationLocation location,
        AccommodationType type,
        double rating,
        int numOrReviews
) {

    public static AccommodationListResponse of(AccommodationEntity accomodation) {
        return new AccommodationListResponse(
                accomodation.getId(),
                accomodation.getName(),
                !accomodation.getPictures().isEmpty() ? accomodation.getPictures().get(0).getUrl() : null,
                accomodation.getAddress(),
                accomodation.getType(),
                accomodation.rating(),
                accomodation.reviews().size()
        );
    }
}
