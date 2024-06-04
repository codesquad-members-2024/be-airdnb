package team07.airbnb.domain.accomodation.dto;

import team07.airbnb.domain.accomodation.AccomodationEntity;
import team07.airbnb.domain.accomodation.property.AccommodationLocation;
import team07.airbnb.domain.accomodation.property.AccomodationType;

public record AccomodationListResponse (
        long id,
        String name,
        String imageUrl,
        AccommodationLocation location,
        AccomodationType type
){

    public static AccomodationListResponse of(AccomodationEntity accomodation){
        return new AccomodationListResponse(
                accomodation.getId(),
                accomodation.getName(),
                !accomodation.getImages().isEmpty() ? accomodation.getImages().get(0).getUrl() : null,
                accomodation.getAddress(),
                accomodation.getType()
        );
    }
}
