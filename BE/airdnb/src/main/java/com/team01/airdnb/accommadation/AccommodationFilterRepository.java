package com.team01.airdnb.accommadation;

import com.team01.airdnb.accommadation.dto.AccommodationSearchResponse;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationFilterRepository {
  List<AccommodationSearchResponse> afterCheckin(LocalDate checkin, LocalDate checkout);
  List<AccommodationSearchResponse> beforeCheckout();
  List<AccommodationSearchResponse> afterMinCost();
  List<AccommodationSearchResponse> beforeMaxCost();
  List<AccommodationSearchResponse> underMaxAdult();
  List<AccommodationSearchResponse> underMaxChildren();
  List<AccommodationSearchResponse> underMaxInfants();
}
