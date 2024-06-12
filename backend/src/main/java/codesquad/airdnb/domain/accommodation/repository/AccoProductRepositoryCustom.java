package codesquad.airdnb.domain.accommodation.repository;

import codesquad.airdnb.domain.accommodation.dto.response.FilteredAccosResponse;

import java.time.LocalDate;
import java.util.List;

public interface AccoProductRepositoryCustom {
    void createYearlyProduct(Long accoId, Long price);

    void createNextProductForAllAcco();

    List<FilteredAccosResponse> getAccoListFilteredBy(List<Long> accoId, LocalDate checkInDate, LocalDate checkOutDate);
}
