package team10.airdnb.accommodation_amenity.dto;

import team10.airdnb.accommodation_amenity.entity.AccommodationAmenity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record AccommodationAmenityDto(
        List<String> amenityNames
) {
    public static AccommodationAmenityDto from(Set<AccommodationAmenity> accommodationAmenities) {
        return new AccommodationAmenityDto(
                accommodationAmenities.stream()
                        .map(accommodationAmenity -> accommodationAmenity.getAmenity().getName())
                        .collect(Collectors.toList())
        );
    }
}
