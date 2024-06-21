package team8.airbnb.offereditems;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferedItemRepository extends JpaRepository<OfferedItem, Long> {
}
