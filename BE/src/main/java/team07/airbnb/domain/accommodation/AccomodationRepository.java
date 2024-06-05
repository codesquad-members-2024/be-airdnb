package team07.airbnb.domain.accommodation;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team07.airbnb.domain.accommodation.entity.AccomodationEntity;

import java.util.List;

public interface AccomodationRepository extends JpaRepository<AccomodationEntity, Long> {

    @Query(value = "SELECT * FROM accomodation WHERE ST_Distance_Sphere(point, :point) <= :distance", nativeQuery = true)
    List<AccomodationEntity> findByLocationWithinDistance(@Param("point") Point point, @Param("distance") double distance);
}
