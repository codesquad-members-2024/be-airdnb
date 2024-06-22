package codesquad.team05.domain.servicecharge;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceChargeRepository extends JpaRepository<ServiceCharge, Long> {

    List<ServiceCharge> findByAccommodationId(Long accommodationId);
}
