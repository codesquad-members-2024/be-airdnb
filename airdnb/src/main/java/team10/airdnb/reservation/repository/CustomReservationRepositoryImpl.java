package team10.airdnb.reservation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import team10.airdnb.reservation.entity.QReservation;
import team10.airdnb.reservation.entity.Reservation;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class CustomReservationRepositoryImpl implements CustomReservationRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Reservation> findConflictingReservations(Long accommodationId, LocalDate checkInDate, LocalDate checkOutDate) {
        QReservation reservation = QReservation.reservation;

        return queryFactory.selectFrom(reservation)
                .where(
                        reservation.accommodation.id.eq(accommodationId)
                                .and(reservation.checkInDate.lt(checkOutDate)
                                        .and(reservation.checkOutDate.gt(checkInDate)))
                )
                .fetch();
    }
}
