package team07.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team07.airbnb.data.product.ProductStatus;
import team07.airbnb.entity.ProductEntity;

import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findAllByAccommodationIdInAndStatus(List<Long> accommodationIds, ProductStatus status);

    List<ProductEntity> findAllByAccommodationIdAndDateBetween(Long accommodationId, LocalDate checkIn, LocalDate checkOut);
}
