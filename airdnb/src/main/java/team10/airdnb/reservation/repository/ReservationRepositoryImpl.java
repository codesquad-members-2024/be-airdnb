package team10.airdnb.reservation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static team10.airdnb.reservation.entity.QReservation.*;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public boolean isDateRangeAvailable(Long accommodationId, LocalDate checkInDate, LocalDate checkOutDate) {

        boolean exists = queryFactory.selectFrom(reservation)
                .where(
                        reservation.accommodation.id.eq(accommodationId)
                                .and(
                                        reservation.checkInDate.loe(checkOutDate)
                                                .and(reservation.checkOutDate.goe(checkInDate))
                                )
                                .and(
                                        reservation.checkInDate.ne(checkOutDate)
                                )
                                .and(
                                        reservation.checkOutDate.ne(checkInDate)
                                )
                )
                .fetchCount() > 0;

        return !exists;

    }
}


