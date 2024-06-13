package team10.airdnb.accommodation.dto;

import team10.airdnb.accommodation.entity.Accommodation;

public record AccommodationInformationDto(
        long accommodationId,
        String accommodationName
) {
    public static AccommodationInformationDto from(Accommodation accommodation) {
        return new AccommodationInformationDto(
                accommodation.getId(),
                accommodation.getName()
        );
    }
    
}
