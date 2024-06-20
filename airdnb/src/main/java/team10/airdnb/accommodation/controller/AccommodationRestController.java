package team10.airdnb.accommodation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.airdnb.accommodation.controller.request.AccommodationCreateRequest;
import team10.airdnb.accommodation.controller.response.AccommodationCreateResponse;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.service.AccommodationService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccommodationRestController {

    private final AccommodationService accommodationService;

    @GetMapping("/api/accommodations")
    public ResponseEntity<?> getAccommodations() {
        log.info("전체 숙소 조회");

        return ResponseEntity.ok(accommodationService.getAccommodations());
    }

    @GetMapping("/api/accommodation/{accommodationId}")
    public ResponseEntity<Accommodation> getAccommodation(@PathVariable(name = "accommodationId") Long accommodationId) {

        Accommodation accommodation = accommodationService.getAccommodation(accommodationId);

        log.info("숙소 조회 완료 : # {} : 저장된 이름 : {}", accommodation.getId(), accommodation.getName());

        return ResponseEntity.ok(accommodation);
    }

    @PostMapping("/api/accommodation")
    public ResponseEntity<?> createAccommodation(@RequestBody @Valid AccommodationCreateRequest request) {

        AccommodationCreateResponse response = accommodationService.createAccommodation(request);

        log.info("숙소 생성 완료 : # {} : 저장된 이름 : {}, 저장된 편의시설 : {}",
                response.accommodation().getId(),
                response.accommodation().getName(),
                response.amenities().amenityNames().toString());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/accommodation/search")
    public ResponseEntity<?> searchAccommodations(
            @RequestParam(name = "max_capacity", required = false) Long maxCapacity,
            @RequestParam(name = "min_dayrate", required = false) BigDecimal minDayRate,
            @RequestParam(name = "max_dayrate", required = false) BigDecimal maxDayRate,
            @RequestParam(name = "checkin_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(name = "checkout_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate) {

        return ResponseEntity.ok(accommodationService.getFilteredAccommodations(maxCapacity, minDayRate, maxDayRate, checkInDate, checkOutDate));

    }

}
