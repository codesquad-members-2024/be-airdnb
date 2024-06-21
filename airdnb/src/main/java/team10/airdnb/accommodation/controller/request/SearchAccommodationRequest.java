package team10.airdnb.accommodation.controller.request;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SearchAccommodationRequest(
        @RequestParam(name = "max_capacity", required = false) Long maxCapacity,
        @RequestParam(name = "min_dayrate", required = false) BigDecimal minDayRate,
        @RequestParam(name = "max_dayrate", required = false) BigDecimal maxDayRate,
        @RequestParam(name = "checkin_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
        @RequestParam(name = "checkout_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate
) {}
