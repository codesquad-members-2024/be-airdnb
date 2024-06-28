package codesquad.airdnb.domain.accommodation.repository;

import codesquad.airdnb.domain.accommodation.dto.additionals.FilteredAcco;
import codesquad.airdnb.domain.accommodation.dto.response.FilteredAccosResponse;
import codesquad.airdnb.domain.accommodation.entity.*;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

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
    public List<FilteredAcco> getAccoListFilteredBy(List<Long> accoIds, LocalDate checkInDate, LocalDate checkOutDate, Integer lowestPrice, Integer highestPrice) {
        QAccoProduct qAccoProduct = QAccoProduct.accoProduct;
        QAccommodation qAccommodation = QAccommodation.accommodation;

        long daysBetween = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        return queryFactory.select(Projections.constructor(FilteredAcco.class,
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
                        /*
                        List<LocalDate> dates = checkInDate.datesUntil(checkOutDate).toList();

                        .and(qAccoProduct.reserveDate.in(dates))
                        이렇게 IN절을 사용해 확인하는 법도 있는데, 연속된 기간을 체크하는 것이기에 between을 채택!
                         */
                        .and(qAccoProduct.reserveDate.between(checkInDate, checkOutDate.minusDays(1)))
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
                // 1박당 가격으로 필터링
                .having(qAccoProduct.price.sum().divide(daysBetween).between(lowestPrice, highestPrice)
                        // 검색범위의 모든 날에 숙소를 예약할 수 있는지 날짜와 상품개수로 비교한다.
                        .and(qAccoProduct.reserveDate.count().eq(daysBetween)))
                .fetch();
    }
}
