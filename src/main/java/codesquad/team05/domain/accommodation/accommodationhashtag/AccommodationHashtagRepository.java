package codesquad.team05.domain.accommodation.accommodationhashtag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccommodationHashtagRepository extends JpaRepository<AccommodationHashtag, Long> {

    boolean existsByAccommodationHashtagId(AccommodationHashtagId accommodationHashtagId);

    Optional<AccommodationHashtag> findByAccommodationHashtagId(AccommodationHashtagId accommodationHashtagId);
}
