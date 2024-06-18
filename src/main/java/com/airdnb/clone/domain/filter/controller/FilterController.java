package com.airdnb.clone.domain.filter.controller;

import com.airdnb.clone.domain.filter.service.FilterService;
import com.airdnb.clone.domain.stay.controller.dto.response.StayDetailResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class FilterController {

    private static final long DEFAULT_INTERVAL_DAYS = 7L;
    private final FilterService filterService;

    @GetMapping("/stays")
    public List<StayDetailResponse> getAvailableStays(
            @RequestParam(required = false) LocalDate checkInDate,
            @RequestParam(required = false) LocalDate checkOutDate,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer guestCount) {
        if (checkInDate == null) {
            checkInDate = LocalDate.now();
        }
        if (checkOutDate == null) {
            checkOutDate = checkInDate.plusDays(DEFAULT_INTERVAL_DAYS);
        }
        return filterService.getAvailableStays(checkInDate, checkOutDate, minPrice, maxPrice, guestCount);
    }
}
