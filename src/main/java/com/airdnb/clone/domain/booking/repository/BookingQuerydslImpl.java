package com.airdnb.clone.domain.booking.repository;

import static com.airdnb.clone.domain.booking.entity.QBooking.booking;
import static com.airdnb.clone.domain.stay.entity.QStay.stay;

import com.airdnb.clone.domain.stay.entity.Stay;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.TimeTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookingQuerydslImpl implements BookingQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Stay> findAvailableStays(LocalDate checkInDate, LocalDate checkOutDate, Integer minPrice, Integer maxPrice, Integer guestCount) {
        return jpaQueryFactory
                .select(stay)
                .from(booking)
                .rightJoin(stay)
                .on(booking.stay.id.eq(stay.id).and(matchesCheckInAndCheckOut(checkInDate, checkOutDate)))
                .where(
                        matchesTotalPrice(minPrice, maxPrice),
                        matchesGuestCount(guestCount),
                        booking.id.isNull())
                .fetch();
    }

    private BooleanExpression matchesTotalPrice(Integer minPrice, Integer maxPrice) {
        if (minPrice == null && maxPrice == null) {
            return null;
        }
        if (minPrice == null) {
            return stay.fee.perNight.loe(maxPrice);
        }
        if (maxPrice == null) {
            return stay.fee.perNight.goe(minPrice);
        }
        return stay.fee.perNight.between(minPrice, maxPrice);
    }

    private BooleanExpression matchesGuestCount(Integer guestCount) {
        if (guestCount == null) {
            return null;
        }
        return stay.roomInfo.guest.guestCount.goe(guestCount);
    }

    private BooleanExpression matchesCheckInAndCheckOut(LocalDate checkInDate, LocalDate checkOutDate) {

        if (checkInDate == null || checkOutDate == null) {
            return null;
        }
        if (checkInDate.isAfter(checkOutDate)) {
            throw new IllegalArgumentException();
        }

        DateTemplate<LocalDate> bookedCheckInDate = Expressions.dateTemplate(LocalDate.class,
                "CAST({0} AS DATE)", booking.checkIn);

        TimeTemplate<LocalTime> bookedCheckInTime = Expressions.timeTemplate(LocalTime.class,
                "CAST({0} AS TIME)", booking.checkIn);

        DateTemplate<LocalDate> bookedCheckOutDate = Expressions.dateTemplate(LocalDate.class,
                "CAST({0} AS DATE)", booking.checkOut);

        TimeTemplate<LocalTime> bookedCheckOutTime = Expressions.timeTemplate(LocalTime.class,
                "CAST({0} AS TIME)", booking.checkOut);

      return  bookedCheckInDate.lt(checkOutDate)
                .or(bookedCheckInDate.eq(checkOutDate).and(bookedCheckInTime.lt(booking.stay.checkOutTime)))
                .and(bookedCheckOutDate.gt(checkInDate)
                        .or(bookedCheckOutDate.eq(checkInDate).and(bookedCheckOutTime.gt(booking.stay.checkInTime))));
    }
}
