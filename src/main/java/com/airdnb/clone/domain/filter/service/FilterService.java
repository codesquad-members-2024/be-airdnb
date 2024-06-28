package com.airdnb.clone.domain.filter.service;

import com.airdnb.clone.domain.booking.repository.BookingRepository;
import com.airdnb.clone.domain.stay.controller.dto.response.StayDetailResponse;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FilterService {

    private final BookingRepository bookingRepository;

    public Slice<StayDetailResponse> getAvailableStaysByCursor(LocalDate checkInDate, LocalDate checkOutDate,
                                                               Integer minPrice, Integer maxPrice, Integer guestCount,
                                                               Long cursorId, Pageable pageable) {

        return bookingRepository.findAvailableStaysByCursor(checkInDate,
                checkOutDate, minPrice, maxPrice, guestCount, cursorId, pageable);
    }

    public Page<StayDetailResponse> getAvailableStaysByOffset(LocalDate checkInDate, LocalDate checkOutDate,
                                                              Integer minPrice, Integer maxPrice, Integer guestCount,
                                                              Pageable pageable) {

        return bookingRepository.findAvailableStaysByOffset(checkInDate,
                checkOutDate, minPrice, maxPrice, guestCount, pageable);
    }
}
