package team10.airdnb.amenity.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.airdnb.amenity.controller.request.AmenityRequest;
import team10.airdnb.amenity.service.AmenityService;
import team10.airdnb.amenity.entity.Amenity;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AmenityRestController {

    private final AmenityService amenityService;

    @GetMapping("/amenities")
    public List<Amenity> getAllAmenity() {
        log.info("저장된 전체 Amenity 로드");
        return amenityService.getAllAmenity();
    }

    //새로운 Amenity 추가
    @PostMapping("/amenity")
    public ResponseEntity<Amenity> saveAmenity(@RequestBody @Valid AmenityRequest request) {
        Amenity response = amenityService.saveAmenity(request);
        log.info("Amenity 저장 완료, # {} :  저장된 이름 : {}", response.getId(), response.getName());
        return ResponseEntity.ok(response);
    }

    //Amenity 수정
    @PatchMapping("/amenity/{amenityId}")
    public ResponseEntity<Amenity> updateAmenity(@RequestBody @Valid AmenityRequest request,
                                                 @PathVariable("amenityId") long amenityId) {
        Amenity updatedAmenity = amenityService.updateAmenity(amenityId, request);
        log.info("Amenity 수정 완료, # {} : 수정된 이름 : {}", updatedAmenity.getId(), updatedAmenity.getName());
        return ResponseEntity.ok(updatedAmenity);
    }

    //Amenity 삭제
    @DeleteMapping("/amenity/{amenityId}")
    public ResponseEntity<Amenity> deleteAmenity(@PathVariable("amenityId") long amenityId) {
        Amenity deletedAmenity = amenityService.deleteAmenity(amenityId);
        log.info("Amenity 삭제 완료, # {} : 삭제된 이름 : {}", deletedAmenity.getId(), deletedAmenity.getName());
        return ResponseEntity.ok(deletedAmenity);
    }


}
