package team07.airbnb.controller;

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