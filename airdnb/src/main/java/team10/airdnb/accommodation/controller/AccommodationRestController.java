package team10.airdnb.accommodation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team10.airdnb.accommodation.controller.request.AccommodationCreateRequest;
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
}
