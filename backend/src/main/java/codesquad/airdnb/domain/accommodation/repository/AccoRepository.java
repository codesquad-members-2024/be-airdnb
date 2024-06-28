package codesquad.airdnb.domain.accommodation.repository;

import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AccoRepository extends JpaRepository<Accommodation, Long>, AccoRepositoryCustom {

    @Query(value = """
            SELECT ID FROM ACCOMMODATION \
            WHERE ST_Distance_Sphere(COORDINATE, :point) <= 100000000 \
            AND MAX_GUEST_COUNT >= :maxGuestCount""", nativeQuery = true)
//            AND MAX_INFANT_COUNT >= :maxInfantCount)
    List<Long> findIdsByCoordAndHumanCount(Point point, Integer maxGuestCount);

    List<Accommodation> findAllByHostAccountName(String accountName);
}
