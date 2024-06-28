package codesquad.airdnb.domain.accommodation.repository;

import codesquad.airdnb.domain.accommodation.entity.AccoProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AccoProductRepository extends JpaRepository<AccoProduct, Long>, AccoProductRepositoryCustom {

    List<AccoProduct> findByAccommodation_IdAndReserveDateBetweenAndIsReservedFalse(Long accoId, LocalDate startDate, LocalDate endDate);
}
