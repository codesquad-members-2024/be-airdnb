package team10.airdnb.accommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team10.airdnb.accommodation.entity.Accommodation;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
}
