package com.team01.airdnb.accommadation.dto;

import java.time.LocalDate;

public record AccommodationFilterRequest(
    LocalDate checkin,
    LocalDate checkout,
    Long minPrice,
    Long maxPrice,
    Integer adultCount,
    Integer childrenCount,
    Integer infantsCount,
    String location,
    Double latitude,
    Double longitude
) {

}
