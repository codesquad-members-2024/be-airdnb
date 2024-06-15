package codesquad.airdnb.domain.accommodation.repository;

import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccoRepository extends JpaRepository<Accommodation, Long> {

    List<Accommodation> findAllByHostId(Long hostId);

    @Query(value = """
            SELECT ID FROM ACCOMMODATION \
            WHERE ST_Distance_Sphere(COORDINATE, :point) <= 100000000 \
            AND MAX_GUEST_COUNT >= :maxGuestCount \
            AND MAX_INFANT_COUNT >= :maxInfantCount""", nativeQuery = true)
    List<Long> findIdsByCoordAndHumanCount(Point point, Integer maxGuestCount, Integer maxInfantCount);
}
