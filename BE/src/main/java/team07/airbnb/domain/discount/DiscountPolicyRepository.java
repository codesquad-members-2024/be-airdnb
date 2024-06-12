package team07.airbnb.domain.discount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team07.airbnb.domain.discount.entity.DiscountPolicyEntity;

import java.util.Optional;

@Repository
public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicyEntity, Long> {

    Optional<DiscountPolicyEntity> findByDescription(String description);
}
