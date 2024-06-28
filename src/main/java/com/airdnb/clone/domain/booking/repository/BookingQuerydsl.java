package com.airdnb.clone.domain.booking.repository;

import com.airdnb.clone.domain.stay.controller.dto.response.StayDetailResponse;
import com.airdnb.clone.domain.stay.entity.Stay;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BookingQuerydsl {

    List<Stay> findAvailableStays(LocalDate checkInDate, LocalDate checkOutDate, Integer minPrice, Integer maxPrice,
                                  Integer guestCount);

    Slice<StayDetailResponse> findAvailableStaysByCursor(LocalDate checkInDate, LocalDate checkOutDate, Integer minPrice, Integer maxPrice, Integer guestCount, Long cursorId, Pageable pageable);
    Page<StayDetailResponse> findAvailableStaysByOffset(LocalDate checkInDate, LocalDate checkOutDate, Integer minPrice, Integer maxPrice, Integer guestCount, Pageable pageable);
}
