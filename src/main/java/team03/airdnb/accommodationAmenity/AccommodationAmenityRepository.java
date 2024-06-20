package team03.airdnb.accommodationAmenity;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccommodationAmenityRepository extends JpaRepository<AccommodationAmenity, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM AccommodationAmenity aa WHERE aa.accommodation.id = :accommodationId")
    void deleteByAccommodationId(@Param("accommodationId") Long accommodationId);
}
