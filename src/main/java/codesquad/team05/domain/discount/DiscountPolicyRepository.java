package codesquad.team05.domain.discount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy, Long> {

    List<DiscountPolicy> findByStartDate(LocalDate today);

    List<DiscountPolicy> findByEndDate(LocalDate today);

    DiscountPolicy findByAccommodationId(Long accommodationId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM DiscountPolicy dp WHERE dp.endDate = :endDate")
    void deleteByEndDate(LocalDate endDate);


}
