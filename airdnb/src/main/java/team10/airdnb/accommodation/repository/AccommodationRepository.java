package team10.airdnb.accommodation.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import team10.airdnb.accommodation.entity.Accommodation;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long>, AccommodationRepositoryCustom{

    @EntityGraph(attributePaths = {"accommodationType", "accommodationRoomType"})
    List<Accommodation> findAll();
}
