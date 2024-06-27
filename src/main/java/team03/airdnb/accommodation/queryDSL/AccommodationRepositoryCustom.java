package team03.airdnb.accommodation.queryDSL;

import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.dto.request.AccommodationFilterDto;

import java.util.List;

public interface AccommodationRepositoryCustom {

    List<Accommodation> findAccommodationsByFilters(AccommodationFilterDto filterDto);
}