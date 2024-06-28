package com.airdnb.clone.domain.filter.controller;

import com.airdnb.clone.domain.filter.service.FilterService;
import com.airdnb.clone.domain.stay.controller.dto.response.StayDetailResponse;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
    public Slice<StayDetailResponse> getAvailableStays(
            @RequestParam(required = false) LocalDate checkInDate,
            @RequestParam(required = false) LocalDate checkOutDate,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer guestCount,
            @RequestParam(required = false, defaultValue = "1") Long cursorId,
            @PageableDefault(size = 15, sort = {"STAY_ID"}, direction = Direction.DESC) Pageable pageable) {
        if (checkInDate == null) {
            checkInDate = LocalDate.now();
        }
        if (checkOutDate == null) {
            checkOutDate = checkInDate.plusDays(DEFAULT_INTERVAL_DAYS);
        }

        return filterService.getAvailableStaysByCursor(checkInDate, checkOutDate, minPrice, maxPrice, guestCount, cursorId, pageable);
    }

    @GetMapping("/stays/offset")
    public Slice<StayDetailResponse> getAvailableStays(
            @RequestParam(required = false) LocalDate checkInDate,
            @RequestParam(required = false) LocalDate checkOutDate,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer guestCount,
            @PageableDefault(size = 15, sort = {"STAY_ID"}, direction = Direction.DESC) Pageable pageable) {
        if (checkInDate == null) {
            checkInDate = LocalDate.now();
        }
        if (checkOutDate == null) {
            checkOutDate = checkInDate.plusDays(DEFAULT_INTERVAL_DAYS);
        }

        return filterService.getAvailableStaysByOffset(checkInDate, checkOutDate, minPrice, maxPrice, guestCount, pageable);
    }
}
