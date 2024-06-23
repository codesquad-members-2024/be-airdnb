package team10.airdnb.reservation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import team10.airdnb.reservation.entity.QReservation;

import java.time.LocalDate;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public boolean isDateRangeAvailable(Long accommodationId, LocalDate checkInDate, LocalDate checkOutDate) {
        QReservation reservation = QReservation.reservation;

        long count = queryFactory.selectFrom(reservation)
                .where(
                        reservation.accommodation.id.eq(accommodationId)
                                .and(
                                        reservation.checkInDate.lt(checkOutDate)
                                                .and(reservation.checkOutDate.gt(checkInDate))
                                                .or(reservation.checkInDate.eq(checkOutDate)) //체크인과 체크아웃 날짜가 같은 것을 허용
                                                .or(reservation.checkOutDate.eq(checkInDate))
                                )
                )
                .fetchCount();

        return count == 0;
    }
}


