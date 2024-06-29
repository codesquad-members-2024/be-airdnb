package team03.airdnb.accommodation;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team03.airdnb.accommodation.queryDSL.AccommodationRepositoryCustom;
import team03.airdnb.user.User;

import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long>, AccommodationRepositoryCustom {

    @Modifying
    @Transactional
    @Query("UPDATE Accommodation a SET a.averageGrade = :averageGrade WHERE a.id = :id")
    int updateAverageGrade(@Param("id") Long id, @Param("averageGrade") double averageGrade);

    List<Accommodation> findByHost(User host);
}