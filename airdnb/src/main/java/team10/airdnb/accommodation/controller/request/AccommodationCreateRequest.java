package team10.airdnb.accommodation.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation_amenity.entity.AccommodationAmenity;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;
import team10.airdnb.accommodation_type.entity.AccommodationType;

import java.util.List;

public record AccommodationCreateRequest(
        @NotBlank
        @Email
        String memberId,

        @NotBlank
        String name,

        @Min(value = 1) @Max(value = 16)
        long maxCapacity,

        Long accommodationType,

        Long accommodationRoomType,

        @Min(value = 0) @Max(value = 50)
        long bedroomCount,

        @Min(value = 1) @Max(value = 50)
        long bathroomCount,

        @Min(value = 1) @Max(value = 50)
        long bedCount,

        @Min(value = 10000) @Max(value = 15000000)
        long perPrice,

        List<Long> amenityIds
) {
    public Accommodation toEntity(AccommodationType accommodationType, AccommodationRoomType accommodationRoomType) {
        return Accommodation.builder()
                .memberId(memberId)
                .name(name)
                .maxCapacity(maxCapacity)
                .accommodationType(accommodationType)
                .accommodationRoomType(accommodationRoomType)
                .bedroomCount(bedroomCount)
                .bathroomCount(bathroomCount)
                .bedCount(bedCount)
                .perPrice(perPrice)
                .build();
    }
}
