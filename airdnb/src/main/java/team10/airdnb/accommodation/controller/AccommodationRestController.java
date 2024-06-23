package team10.airdnb.accommodation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.airdnb.accommodation.controller.request.AccommodationCreateRequest;
import team10.airdnb.accommodation.controller.request.SearchAccommodationRequest;
import team10.airdnb.accommodation.controller.response.AccommodationCreateResponse;
import team10.airdnb.accommodation.dto.SearchAccommodationDto;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.service.AccommodationService;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccommodationRestController {

    private final AccommodationService accommodationService;

    @GetMapping("/accommodations")
    public ResponseEntity<?> getAccommodations() {
        log.info("전체 숙소 조회");

        return ResponseEntity.ok(accommodationService.getAccommodations());
    }

    @GetMapping("/accommodation/{accommodationId}")
    public ResponseEntity<Accommodation> getAccommodation(@PathVariable(name = "accommodationId") Long accommodationId) {

        Accommodation accommodation = accommodationService.getAccommodation(accommodationId);

        log.info("숙소 조회 완료 : # {} : 저장된 이름 : {}", accommodation.getId(), accommodation.getName());

        return ResponseEntity.ok(accommodation);
    }

    @PostMapping("/accommodation")
    public ResponseEntity<?> createAccommodation(@RequestBody @Valid AccommodationCreateRequest request) {

        AccommodationCreateResponse response = accommodationService.createAccommodation(request);

        log.info("숙소 생성 완료 : # {} : 저장된 이름 : {}, 저장된 편의시설 : {}",
                response.accommodation().getId(),
                response.accommodation().getName(),
                response.amenities().amenityNames().toString());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/accommodation/search")
    public ResponseEntity<Page<SearchAccommodationDto>> searchAccommodations(
            @RequestParam(name = "capacity", required = false) Long capacity,
            @RequestParam(name = "min_dayrate", required = false) BigDecimal minDayRate,
            @RequestParam(name = "max_dayrate", required = false) BigDecimal maxDayRate,
            @RequestParam(name = "checkin_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(name = "checkout_date", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam int page
    ) {
        int size = 20; // 페이지 크기 설정
        Pageable pageable = PageRequest.of(page, size);
        SearchAccommodationRequest request = new SearchAccommodationRequest(capacity, minDayRate, maxDayRate, checkInDate, checkOutDate);

        Page<SearchAccommodationDto> accommodations = accommodationService.getFilteredAccommodations(pageable, request);

        return ResponseEntity.ok(accommodations);
    }
}
