package com.airdnb.clone.domain.booking.repository;

import static com.airdnb.clone.domain.booking.entity.QBooking.booking;
import static com.airdnb.clone.domain.stay.entity.QStay.stay;

import com.airdnb.clone.domain.stay.controller.dto.response.StayDetailResponse;
import com.airdnb.clone.domain.stay.entity.Stay;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.TimeTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class BookingQuerydslImpl implements BookingQuerydsl {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Stay> findAvailableStays(LocalDate checkInDate, LocalDate checkOutDate, Integer minPrice,
                                         Integer maxPrice, Integer guestCount) {
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

    @Override
    public Slice<StayDetailResponse> findAvailableStaysByCursor(LocalDate checkInDate, LocalDate checkOutDate, Integer minPrice,
                                                                Integer maxPrice, Integer guestCount, Long cursorId, Pageable pageable) {
        List<Stay> stays = jpaQueryFactory
                .select(stay)
                .from(booking)
                .rightJoin(stay)
                .on(booking.stay.id.eq(stay.id).and(matchesCheckInAndCheckOut(checkInDate, checkOutDate)))
                .where(
                        cursorId(cursorId),
                        matchesTotalPrice(minPrice, maxPrice),
                        matchesGuestCount(guestCount),
                        booking.id.isNull())
                .limit(pageable.getPageSize() + 1) // Cursor 기반 페이징에서만 사용
                .orderBy(stay.id.asc())
                .fetch();

        /* DTO 변환 -> @QueryProjection 고려하기 */
        List<StayDetailResponse> content = stays.stream()
                .map(StayDetailResponse::of)
                .toList();

        /* Cursor Paging 에서만 사용 */
        boolean hasNext = false;
        int pageSize = pageable.getPageSize();
        if (stays.size() > pageSize) {
            stays.remove(pageSize);
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Page<StayDetailResponse> findAvailableStaysByOffset(LocalDate checkInDate, LocalDate checkOutDate, Integer minPrice,
                                                               Integer maxPrice, Integer guestCount, Pageable pageable) {
        List<Stay> stays = jpaQueryFactory
                .select(stay)
                .from(booking)
                .rightJoin(stay)
                .on(booking.stay.id.eq(stay.id).and(matchesCheckInAndCheckOut(checkInDate, checkOutDate)))
                .where(
                        matchesTotalPrice(minPrice, maxPrice),
                        matchesGuestCount(guestCount),
                        booking.id.isNull())
                .offset(pageable.getOffset()) // OFFSET 기반 페이징에서만 사용
                .limit(pageable.getPageSize())
                .orderBy(stay.id.asc())
                .fetch();

        /* DTO 변환 -> @QueryProjection 고려하기 */
        List<StayDetailResponse> content = stays.stream()
                .map(StayDetailResponse::of)
                .toList();

        /* Offset Paging 에서만 사용 -> 필요할 때만 카운트 쿼리 날림 */
        JPAQuery<Long> countQuery = getCount(checkInDate, checkOutDate, minPrice, maxPrice, guestCount);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private JPAQuery<Long> getCount(LocalDate checkInDate, LocalDate checkOutDate, Integer minPrice,
                                    Integer maxPrice, Integer guestCount) {
        return jpaQueryFactory
                .select(stay.count())
                .from(booking)
                .rightJoin(stay)
                .on(booking.stay.id.eq(stay.id).and(matchesCheckInAndCheckOut(checkInDate, checkOutDate)))
                .where(
                        matchesTotalPrice(minPrice, maxPrice),
                        matchesGuestCount(guestCount),
                        booking.id.isNull()
                );
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
        return stay.roomInfo.guestCount.goe(guestCount);
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

        return bookedCheckInDate.lt(checkOutDate)
                .or(bookedCheckInDate.eq(checkOutDate).and(bookedCheckInTime.lt(booking.stay.checkOutTime)))
                .and(bookedCheckOutDate.gt(checkInDate)
                        .or(bookedCheckOutDate.eq(checkInDate).and(bookedCheckOutTime.gt(booking.stay.checkInTime))));
    }

    private BooleanExpression cursorId(Long cursorId){
        return cursorId == null ? null : stay.id.gt(cursorId);
    }
}
