package team10.airdnb.accommodation.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;
import java.util.Optional;


public record AccommodationUpdateRequest(
        Optional<String> memberId,
        Optional<String> name,
        @Min(1) @Max(16)
        Optional<Long> maxCapacity,
        Optional<Long> accommodationType,
        Optional<Long> accommodationRoomType,
        @Min(0) @Max(50)
        Optional<Integer> bedroomCount,
        @Min(1) @Max(50)
        Optional<Integer> bathroomCount,
        @Min(1) @Max(50)
        Optional<Integer> bedCount,
        @Min(10000) @Max(15000000)
        Optional<Integer> dayRate,
        @Min(0) @Max(1000000)
        Optional<Integer> cleaningFee,
        Optional<String> city,
        Optional<String> district,
        Optional<String> neighborhood,
        Optional<String> streetName,
        Optional<String> detailedAddress,
        Optional<Double> latitude,
        Optional<Double> longitude,
        Optional<List<Long>> amenityIds
) {
}

