package team03.airdnb.accommodation;

import java.util.List;
import team03.airdnb.accommodation.dto.request.AccommodationFilterDto;

public interface AccommodationRepositoryCustom {
    List<Accommodation> findAccommodationsByFilters(AccommodationFilterDto filterDto);
}