package team10.airdnb.accommodation.repository;

import team10.airdnb.accommodation.entity.Accommodation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface AccommodationRepositoryCustom {
    List<Accommodation> findAvailableAccommodations(Long maxCapacity, BigDecimal minDayRate, BigDecimal maxDayRate, LocalDate checkInDate, LocalDate checkOutDate);
}

