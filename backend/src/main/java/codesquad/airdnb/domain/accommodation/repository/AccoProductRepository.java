package codesquad.airdnb.domain.accommodation.repository;

import codesquad.airdnb.domain.accommodation.entity.AccoProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccoProductRepository extends JpaRepository<AccoProduct, Long>, AccoProductRepositoryCustom {
}
