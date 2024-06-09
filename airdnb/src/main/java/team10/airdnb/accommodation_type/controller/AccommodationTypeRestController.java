package team10.airdnb.accommodation_type.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.airdnb.accommodation_type.controller.request.AccommodationTypeCreateRequest;
import team10.airdnb.accommodation_type.controller.request.AccommodationTypeUpdateRequest;
import team10.airdnb.accommodation_type.entity.AccommodationType;
import team10.airdnb.accommodation_type.service.AccommodationTypeService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccommodationTypeRestController {

    private final AccommodationTypeService accommodationTypeService;

    @GetMapping("/api/accommodation-types")
    public List<AccommodationType> getAllAccommodationTypes() {
        log.info("저장된 전체 숙소 유형 목록 로드");

        return accommodationTypeService.getAllAccommodationTypes();
    }

    @PatchMapping("/api/accommodation-type/{accommodationTypeId}")
    public ResponseEntity<?> updateAccommodationType(@RequestBody @Valid AccommodationTypeUpdateRequest request,
                                                     @PathVariable("accommodationTypeId") long accommodationTypeId) {
        AccommodationType updatedAccommodationType = accommodationTypeService.updateAccommodationType(accommodationTypeId, request);

        log.info("숙소 유형 수정 완료,  # {} : 수정된 이름 : {}", updatedAccommodationType.getId(), updatedAccommodationType.getName());

        return ResponseEntity.ok(updatedAccommodationType);
    }

    @DeleteMapping("/api/accommodation-type/{accommodationTypeId}")
    public ResponseEntity<?> deleteAccommodationType(@PathVariable("accommodationTypeId") long accommodationTypeId) {
        AccommodationType deletedAccommodationType = accommodationTypeService.deleteAccommodationType(accommodationTypeId);

        log.info("숙소 유형 삭제 완료, # {} : 삭제된 이름 : {}", deletedAccommodationType.getId(), deletedAccommodationType.getName());

        return ResponseEntity.ok(deletedAccommodationType);
    }

    @PostMapping("/api/accommodation-type")
    public ResponseEntity<?> saveAccommodationType(@RequestBody @Valid AccommodationTypeCreateRequest request) {
        AccommodationType savedAccommodationType = accommodationTypeService.saveAccommodationType(request);

        log.info("숙소 유형 저장 완료, # {} :  저장된 이름 : {}", savedAccommodationType.getId(), savedAccommodationType.getName());

        return ResponseEntity.ok(savedAccommodationType);
    }

}
