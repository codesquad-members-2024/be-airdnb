package team07.airbnb.repository;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.entity.UserEntity;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<AccommodationEntity, Long> {

    @Query(value = "SELECT * FROM ACCOMMODATION WHERE ST_Distance_Sphere(point, :point) <= :distance", nativeQuery = true)
    List<AccommodationEntity> findByLocationWithinDistance(@Param("point") Point point, @Param("distance") double distance);

    boolean findAccommodationEntityByHost(UserEntity host);
}
