package team07.airbnb.domain.accommodation;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team07.airbnb.domain.accommodation.entity.AccommodationEntity;
import team07.airbnb.domain.user.entity.UserEntity;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<AccommodationEntity, Long> {

    @Query(value = "SELECT * FROM accommodation WHERE ST_Distance_Sphere(point, :point) <= :distance", nativeQuery = true)
    List<AccommodationEntity> findByLocationWithinDistance(@Param("point") Point point, @Param("distance") double distance);

    boolean findAccommodationEntityByHost(UserEntity host);
}
