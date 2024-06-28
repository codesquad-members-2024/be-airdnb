package team10.airdnb.accommodation.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team10.airdnb.accommodation.controller.request.SearchAccommodationRequest;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.entity.QAccommodation;
import team10.airdnb.accommodation.entity.embedded.QAccommodationFee;
import team10.airdnb.reservation.entity.QReservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccommodationRepositoryImpl implements AccommodationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Accommodation> findAvailableAccommodations(SearchAccommodationRequest request, Pageable pageable) {
        QAccommodation accommodation = QAccommodation.accommodation;
        QReservation reservation = QReservation.reservation;

        BooleanBuilder builder = buildSearchConditions(request, accommodation, reservation);

        NumberTemplate<Double> haversineDistance = Expressions.numberTemplate(Double.class,
                "6371 * acos(cos(radians({0})) * cos(radians({1})) * cos(radians({2}) - radians({3})) + sin(radians({0})) * sin(radians({1})))",
                request.latitude(), accommodation.coordinate.latitude, accommodation.coordinate.longitude, request.longitude());

        JPAQuery<Accommodation> query = queryFactory.selectFrom(accommodation)
                .leftJoin(reservation).on(reservation.accommodation.eq(accommodation))
                .where(builder)
                .orderBy(haversineDistance.asc()); // 가까운 순으로 정렬

        long total = query.fetchCount(); // 전체 개수 조회

        List<Accommodation> accommodations = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(accommodations, pageable, total);
    }

    private BooleanBuilder buildSearchConditions(SearchAccommodationRequest request, QAccommodation accommodation, QReservation reservation) {
        BooleanBuilder builder = new BooleanBuilder();

        addCapacityCondition(builder, request.capacity(), accommodation);
        addDayRateCondition(builder, request.minDayRate(), request.maxDayRate(), accommodation);
        addDateCondition(builder, request.checkInDate(), request.checkOutDate(), reservation);
        addLocationCondition(builder, request.latitude(), request.longitude(), accommodation, request.radius());

        return builder;
    }

    private void addCapacityCondition(BooleanBuilder builder, Long capacity, QAccommodation accommodation) {
        if (capacity != null && capacity > 0) {
            builder.and(accommodation.maxCapacity.goe(capacity));
        }
    }

    private void addDayRateCondition(BooleanBuilder builder, BigDecimal minDayRate, BigDecimal maxDayRate, QAccommodation accommodation) {
        QAccommodationFee accommodationFee = accommodation.accommodationFee;

        if (minDayRate != null && minDayRate.compareTo(BigDecimal.ZERO) > 0) {
            builder.and(accommodationFee.dayRate.goe(minDayRate));
        }
        if (maxDayRate != null && maxDayRate.compareTo(BigDecimal.ZERO) > 0) {
            builder.and(accommodationFee.dayRate.loe(maxDayRate));
        }
    }

    private void addDateCondition(BooleanBuilder builder, LocalDate checkInDate, LocalDate checkOutDate, QReservation reservation) {
        if (checkInDate != null && checkOutDate != null) {
            builder.and(
                    reservation.checkInDate.notBetween(checkInDate, checkOutDate)
                            .and(reservation.checkOutDate.notBetween(checkInDate, checkOutDate))
                            .or(reservation.checkInDate.goe(checkOutDate))
                            .or(reservation.checkOutDate.loe(checkInDate))
                            .or(reservation.accommodation.id.isNull())  // Reservation table에 없는 경우도 예약이 가능하므로 추가
            );
        }
    }

    private void addLocationCondition(BooleanBuilder builder, Double latitude, Double longitude, QAccommodation accommodation, Double radius) {
        if (latitude != null && longitude != null && radius != null) {
            NumberTemplate<Double> haversineDistance = Expressions.numberTemplate(Double.class,
                    "6371 * acos(cos(radians({0})) * cos(radians({1})) * cos(radians({2}) - radians({3})) + sin(radians({0})) * sin(radians({1})))",
                    latitude, accommodation.coordinate.latitude, accommodation.coordinate.longitude, longitude);

            builder.and(haversineDistance.loe(radius));
        }
    }
}
