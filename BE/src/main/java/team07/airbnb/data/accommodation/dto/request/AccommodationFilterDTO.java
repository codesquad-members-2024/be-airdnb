package team07.airbnb.data.accommodation.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AccommodationFilterDTO(
        Integer minPrice,
        Integer maxPrice,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer headCount,
        @NotNull
        Double latitude,
        @NotNull
        Double longitude,
        @NotNull
        Double distance
) {
}