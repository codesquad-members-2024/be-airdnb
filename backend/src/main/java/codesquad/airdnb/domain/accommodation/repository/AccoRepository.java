package codesquad.airdnb.domain.accommodation.repository;

import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccoRepository extends JpaRepository<Accommodation, Long>, AccoRepositoryCustom {
}
