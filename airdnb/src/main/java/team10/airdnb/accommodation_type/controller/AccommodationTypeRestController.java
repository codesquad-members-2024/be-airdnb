package team10.airdnb.accommodation_type.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team10.airdnb.accommodation_type.controller.request.AccommodationTypeCreateRequest;
import team10.airdnb.accommodation_type.entity.AccommodationType;
import team10.airdnb.accommodation_type.service.AccommodationTypeService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accommodation-type")
public class AccommodationTypeRestController {

    private final AccommodationTypeService accommodationTypeService;

    @PostMapping
    public ResponseEntity<?> saveAccommodationType(@RequestBody @Valid AccommodationTypeCreateRequest request) {
        AccommodationType response = accommodationTypeService.saveAccommodationType(request);

        log.info("숙소 유형 저장 완료, # {} :  저장된 이름 : {}", response.getId(), response.getName());

        return ResponseEntity.ok(response);
    }

}
