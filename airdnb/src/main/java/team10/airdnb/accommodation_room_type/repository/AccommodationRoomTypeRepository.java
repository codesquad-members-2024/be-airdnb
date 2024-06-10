package team10.airdnb.accommodation_room_type.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;

import java.util.List;
import java.util.Optional;

public interface AccommodationRoomTypeRepository extends JpaRepository<AccommodationRoomType, Long> {
    Optional<AccommodationRoomType> findByName(String name);
    List<AccommodationRoomType> findAll(Sort sort);
}
