package team10.airdnb.accommodation.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;
import team10.airdnb.accommodation_type.entity.AccommodationType;

import java.util.List;
import java.util.Optional;


public record AccommodationUpdateRequest(
        Optional<String> memberId,
        Optional<String> name,
        @Min(value = 1) @Max(value = 16)
        Optional<Long> maxCapacity,
        Optional<Long> accommodationType,
        Optional<Long> accommodationRoomType,
        @Min(value = 0) @Max(value = 50)
        Optional<Long> bedroomCount,
        @Min(value = 1) @Max(value = 50)
        Optional<Long> bathroomCount,
        @Min(value = 1) @Max(value = 50)
        Optional<Long> bedCount,
        @Min(value = 10000) @Max(value = 15000000)
        Optional<Long> perPrice,
        Optional<List<Long>> amenityIds
) {
}

