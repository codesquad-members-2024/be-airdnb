package team10.airdnb.reservation.dto;

import team10.airdnb.accommodation.entity.Accommodation;

public record AccommodationInformationDto(
        Long accommodationId,
        String accommodationName
) {
    public static AccommodationInformationDto from(Accommodation accommodation) {
        return new AccommodationInformationDto(
                accommodation.getId(),
                accommodation.getName()
        );
    }
    
}
