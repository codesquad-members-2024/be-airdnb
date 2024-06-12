package team03.airdnb.accommodation;

import static team03.airdnb.accommodation.QAccommodation.accommodation;
import static team03.airdnb.reservation.QReservation.reservation;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AccommodationRepositoryImpl implements AccommodationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public AccommodationRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Accommodation> findAccommodationsByFilters(LocalDate checkIn, LocalDate checkOut, Double minPrice,
                                                           Double maxPrice, Integer capacity) {
        // BooleanBuilder 초기화
        BooleanBuilder builder = new BooleanBuilder();

        if (checkIn != null && checkOut != null) {
            // 서브쿼리 조건 작성
            BooleanExpression subQueryCondition = reservation.accommodation.id.eq(accommodation.id)
                    .and(reservation.checkIn.loe(checkOut))
                    .and(reservation.checkOut.goe(checkIn));

            // NOT EXISTS 조건 추가
            builder.and(JPAExpressions.selectOne()
                    .from(reservation)
                    .where(subQueryCondition)
                    .notExists());
        }

        // 기타 조건 추가
        if (minPrice != null) {
            builder.and(accommodation.price.goe(minPrice));
        }

        if (maxPrice != null) {
            builder.and(accommodation.price.loe(maxPrice));
        }

        if (capacity != null) {
            builder.and(accommodation.maxHeadCount.goe(capacity));
        }

        // 주어진 조건에 따라 숙박 목록 조회
        return queryFactory.selectFrom(accommodation)
                .where(builder)
                .fetch();
    }
}
