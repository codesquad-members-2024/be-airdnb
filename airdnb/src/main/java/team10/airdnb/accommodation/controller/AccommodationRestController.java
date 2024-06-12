package team10.airdnb.accommodation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team10.airdnb.accommodation.controller.request.AccommodationCreateRequest;
import team10.airdnb.accommodation.controller.request.AccommodationUpdateRequest;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.service.AccommodationService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccommodationRestController {

    private final AccommodationService accommodationService;

    @PostMapping("/api/accommodation")
    public ResponseEntity<?> createAccommodation(@RequestBody @Valid AccommodationCreateRequest request) {
        Accommodation savedAccommodation = accommodationService.createAccommodation(request);

        log.info("숙소 생성 완료 : # {} : 저장된 이름 : {}", savedAccommodation.getId(), savedAccommodation.getName());

        return ResponseEntity.ok(savedAccommodation);
    }

    @PatchMapping("/api/accommodation/{accommodationId}")
    public ResponseEntity<?> updateAccommodation(@PathVariable Long accommodationId, @RequestBody AccommodationUpdateRequest request) {
        Accommodation updatedAccommodation = accommodationService.updateAccommodation(accommodationId, request);

        log.info("숙소 수정 완료 : # {} : 저장된 이름 : {}", updatedAccommodation.getId(), updatedAccommodation.getName());

        return ResponseEntity.ok(updatedAccommodation);
    }
}
