package team10.airdnb.amenity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team10.airdnb.amenity.entity.Amenity;

import java.util.List;
import java.util.Optional;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    Optional<Amenity> findByName(String name);
    List<Amenity> findAll();
}
