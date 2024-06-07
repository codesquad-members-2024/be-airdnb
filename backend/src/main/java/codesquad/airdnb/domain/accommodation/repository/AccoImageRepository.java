package codesquad.airdnb.domain.accommodation.repository;

import codesquad.airdnb.domain.accommodation.entity.AccoImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccoImageRepository extends JpaRepository<AccoImage, Long> {

    List<AccoImage> findAccoImageByAccommodation_Id(Long accoId);
}
