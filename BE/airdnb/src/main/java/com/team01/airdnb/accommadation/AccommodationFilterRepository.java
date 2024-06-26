package com.team01.airdnb.accommadation;

import com.team01.airdnb.accommadation.dto.AccommodationSearchResponse;
import java.time.LocalDate;
import java.util.List;

public interface AccommodationFilterRepository {
  public List<AccommodationSearchResponse> filterAccommodation(LocalDate checkin,
      LocalDate checkout,
      Long minPrice, Long maxPrice, Integer adultCount, Integer childrenCount,
      Integer infantsCount, String location, Double latitude, Double longitude);
}
