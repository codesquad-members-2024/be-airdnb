package team10.airdnb.accommodation.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

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
        Optional<Integer> bedroomCount,
        @Min(value = 1) @Max(value = 50)
        Optional<Integer> bathroomCount,
        @Min(value = 1) @Max(value = 50)
        Optional<Integer> bedCount,
        @Min(10000) @Max(15000000)
        Optional<Integer> dayRate,
        @Min(0) @Max(1000000)
        Optional<Integer> cleaningFee,
        Optional<List<Long>> amenityIds
) {
}

