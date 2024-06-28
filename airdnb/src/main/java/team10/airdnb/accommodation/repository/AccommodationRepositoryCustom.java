package team10.airdnb.accommodation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team10.airdnb.accommodation.controller.request.SearchAccommodationRequest;
import team10.airdnb.accommodation.entity.Accommodation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface AccommodationRepositoryCustom {
    Page<Accommodation> findAvailableAccommodations(SearchAccommodationRequest request, Pageable pageable);
}

