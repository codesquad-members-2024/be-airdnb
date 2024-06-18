package com.airdnb.clone.domain.filter.service;

import com.airdnb.clone.domain.booking.repository.BookingRepository;
import com.airdnb.clone.domain.stay.controller.dto.response.StayDetailResponse;
import com.airdnb.clone.domain.stay.entity.Stay;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilterService {

    private final BookingRepository bookingRepository;

    public List<StayDetailResponse> getAvailableStays(LocalDate checkInDate, LocalDate checkOutDate, Integer minPrice,
                                                      Integer maxPrice, Integer guestCount) {

        List<Stay> availableStays = bookingRepository.findAvailableStays(
                checkInDate, checkOutDate, minPrice, maxPrice, guestCount);

        return availableStays.stream()
                .map(StayDetailResponse::of)
                .toList();
    }
}
