package team03.airdnb.accommodation;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import team03.airdnb.accommodation.dto.request.AccommodationFilterDto;
import team03.airdnb.reservation.QReservation;

import java.time.LocalDate;
import java.util.List;

@Repository
public class AccommodationRepositoryImpl implements AccommodationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public AccommodationRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Accommodation> findAccommodationsByFilters(AccommodationFilterDto filterDto) {
        BooleanBuilder builder = new BooleanBuilder();

        LocalDate checkIn = filterDto.getCheckIn();
        LocalDate checkOut = filterDto.getCheckOut();
        Double minPrice = filterDto.getMinPrice();
        Double maxPrice = filterDto.getMaxPrice();
        Integer capacity = filterDto.getCapacity();
        Double latitude = filterDto.getCurrentLatitude();
        Double longitude = filterDto.getCurrentLongitude();

        if (checkIn != null && checkOut != null) {
            // 서브쿼리 조건 작성
            BooleanExpression subQueryCondition = QReservation.reservation.accommodation.id.eq(QAccommodation.accommodation.id)
                    .and(QReservation.reservation.checkIn.loe(checkOut))
                    .and(QReservation.reservation.checkOut.goe(checkIn));

            // NOT EXISTS 조건 추가
            builder.and(JPAExpressions.selectOne()
                    .from(QReservation.reservation)
                    .where(subQueryCondition)
                    .notExists());
        }

        // 기타 조건 추가
        if (minPrice != null) {
            builder.and(QAccommodation.accommodation.price.goe(minPrice));
        }

        if (maxPrice != null) {
            builder.and(QAccommodation.accommodation.price.loe(maxPrice));
        }

        if (capacity != null) {
            builder.and(QAccommodation.accommodation.maxHeadCount.goe(capacity));
        }

        // 위도와 경도 범위 조건 추가
        if (latitude != null && longitude != null) {
            BooleanExpression withinRadiusCondition = QAccommodation.accommodation.latitude.goe(latitude - 0.01)
                    .and(QAccommodation.accommodation.latitude.loe(latitude + 0.01))
                    .and(QAccommodation.accommodation.longitude.goe(longitude - 0.01))
                    .and(QAccommodation.accommodation.longitude.loe(longitude + 0.01));
            builder.and(withinRadiusCondition);
        }

        // 주어진 조건에 따라 숙박 목록 조회
        return queryFactory.selectFrom(QAccommodation.accommodation)
                .where(builder)
                .fetch();
    }
}