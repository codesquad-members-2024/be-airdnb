package team10.airdnb.accommodation.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
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
    public List<Accommodation> findAvailableAccommodations(Long maxCapacity, BigDecimal minDayRate,BigDecimal maxDayRate, LocalDate checkInDate, LocalDate checkOutDate) {
        QAccommodation accommodation = QAccommodation.accommodation;
        QAccommodationFee accommodationFee = accommodation.accommodationFee;
        QReservation reservation = QReservation.reservation;


        //동적으로 where절 생성
        BooleanBuilder builder = new BooleanBuilder();

        if (maxCapacity != null && maxCapacity > 0) {
            builder.and(accommodation.maxCapacity.loe(maxCapacity));
        }

        if (minDayRate != null && minDayRate.compareTo(BigDecimal.ZERO) > 0) {
            builder.and(accommodationFee.dayRate.goe(minDayRate));
        }
        if (maxDayRate != null && maxDayRate.compareTo(BigDecimal.ZERO) > 0) {
            builder.and(accommodationFee.dayRate.loe(maxDayRate));
        }

        if (checkInDate != null && checkOutDate != null) {
            builder.and(
                    reservation.checkInDate.notBetween(checkInDate, checkOutDate)
                            .and(reservation.checkOutDate.notBetween(checkInDate, checkOutDate))
                            .or(reservation.checkInDate.goe(checkOutDate))
                            .or(reservation.checkOutDate.loe(checkInDate))
            );
        }

        return queryFactory.selectFrom(accommodation)
                .leftJoin(reservation).on(reservation.accommodation.eq(accommodation))
                .where(builder)
                .fetch();
    }
}
