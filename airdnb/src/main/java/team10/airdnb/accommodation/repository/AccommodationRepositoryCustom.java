package team10.airdnb.accommodation.repository;

import team10.airdnb.accommodation.controller.request.SearchAccommodationRequest;
import team10.airdnb.accommodation.entity.Accommodation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface AccommodationRepositoryCustom {
    List<Accommodation> findAvailableAccommodations(SearchAccommodationRequest request);
}

