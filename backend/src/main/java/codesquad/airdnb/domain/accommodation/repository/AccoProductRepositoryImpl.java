package codesquad.airdnb.domain.accommodation.repository;

import codesquad.airdnb.domain.accommodation.dto.response.FilteredAccosResponse;
import codesquad.airdnb.domain.accommodation.entity.QAccoProduct;
import codesquad.airdnb.domain.accommodation.entity.QAccommodation;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccoProductRepositoryImpl implements AccoProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final EntityManager entityManager;
    @Override
    @Transactional
    public void createYearlyProduct(Long accoId, Long price) {
        entityManager.createNativeQuery("CALL createYearlyProduct(:accoId, :price)")
           .setParameter("accoId", accoId)
                .setParameter("price", price)
                .executeUpdate();
    }
    @Override
    @Transactional
    public void createNextProductForAllAcco() {
        entityManager.createNativeQuery("CALL createNextProductForAllAcco()")
                .executeUpdate();
    }


    @Override
    public List<FilteredAccosResponse> getAccoListFilteredBy(List<Long> accoIds, LocalDate checkInDate, LocalDate checkOutDate) {
        QAccoProduct qAccoProduct = QAccoProduct.accoProduct;
        QAccommodation qAccommodation = QAccommodation.accommodation;

        return queryFactory.select(Projections.constructor(FilteredAccosResponse.class,
                        qAccommodation.id,
                        qAccommodation.title,
                        qAccommodation.placeCategory,
                        qAccommodation.floorPlan.maxGuestCount,
                        qAccommodation.floorPlan.maxInfantCount,
                        qAccommodation.floorPlan.bedroomCount,
                        qAccommodation.floorPlan.bathroomCount,
                        qAccommodation.location.coordinate,
                        qAccoProduct.price.sum())
                )
                .from(qAccoProduct)
                .join(qAccoProduct.accommodation, qAccommodation)
                .where(qAccoProduct.accommodation.id.in(accoIds)
                        .and(qAccoProduct.reserveDate.between(checkInDate, checkOutDate))
                        .and(qAccoProduct.isReserved.isFalse()))
                .groupBy(
                        qAccommodation.id,
                        qAccommodation.title,
                        qAccommodation.placeCategory,
                        qAccommodation.floorPlan.maxGuestCount,
                        qAccommodation.floorPlan.maxInfantCount,
                        qAccommodation.floorPlan.bedroomCount,
                        qAccommodation.floorPlan.bathroomCount,
                        qAccommodation.location.coordinate
                )
                .fetch();
    }
}
