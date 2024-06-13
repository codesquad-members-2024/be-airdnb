package codesquad.airdnb.domain.accommodation.repository;


import codesquad.airdnb.domain.accommodation.entity.ReservationProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationProductRepository extends JpaRepository<ReservationProduct, Long> {

}
