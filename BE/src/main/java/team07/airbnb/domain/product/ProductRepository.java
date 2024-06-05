package team07.airbnb.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import team07.airbnb.domain.product.entity.ProductEntity;
import team07.airbnb.domain.product.entity.ProductStatus;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findAllByAccomodationIdInAndStatus(List<Long> accomodationIds, ProductStatus status);
}
