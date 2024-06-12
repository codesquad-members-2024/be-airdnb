package team03.airdnb.accommodation;

import java.time.LocalDate;
import java.util.List;

public interface AccommodationRepositoryCustom {
    List<Accommodation> findAccommodationsByFilters(LocalDate checkIn, LocalDate checkOut, Double minPrice, Double maxPrice, Integer capacity);
}