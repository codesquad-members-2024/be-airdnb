package team10.airdnb.accommodation_room_type.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.airdnb.accommodation_room_type.controller.request.AccommodationRoomTypeCreateRequest;
import team10.airdnb.accommodation_room_type.controller.request.AccommodationRoomTypeUpdateRequest;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;
import team10.airdnb.accommodation_room_type.service.AccommodationRoomTypeService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AccommodationRoomTypeRestController {

    private final AccommodationRoomTypeService accommodationRoomTypeService;

    @GetMapping("/accommodation-room-types")
    public List<AccommodationRoomType> getAllAccommodationRoomTypes() {
        log.info("저장된 전체 숙소 방 유형 목록 로드");

        return accommodationRoomTypeService.getAllAccommodationRoomTypes();
    }

    @PostMapping("/accommodation-room-type")
    public ResponseEntity<?> saveAccommodationRoomType(@RequestBody @Valid AccommodationRoomTypeCreateRequest request) {

        AccommodationRoomType response = accommodationRoomTypeService.saveAccommodationRoomType(request);

        log.info("숙소 Room Type 저장 완료, # {} :  저장된 이름 : {}", response.getId(), response.getName());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/accommodation-room-type/{accommodationRoomTypeId}")
    public ResponseEntity<?> updateAccommodationRoomType(@RequestBody @Valid AccommodationRoomTypeUpdateRequest request,
                                                         @PathVariable("accommodationRoomTypeId") long accommodationRoomTypeId) {
        AccommodationRoomType updatedAccommodationRoomType = accommodationRoomTypeService.updateAccommodationRoomType(accommodationRoomTypeId, request);

        log.info("숙소 방 유형 수정 완료, # {} : 수정된 이름 : {}", updatedAccommodationRoomType.getId(), updatedAccommodationRoomType.getName());

        return ResponseEntity.ok(updatedAccommodationRoomType);
    }

    @DeleteMapping("/accommodation-room-type/{accommodationRoomTypeId}")
    public ResponseEntity<?> deleteAccommodationRoomType(@PathVariable("accommodationRoomTypeId") long accommodationRoomTypeId) {
        AccommodationRoomType deletedAccommodationRoomType = accommodationRoomTypeService.deleteAccommodationRoomType(accommodationRoomTypeId);

        log.info("숙소 방 유형 삭제 완료, # {} : 삭제된 이름 : {}", deletedAccommodationRoomType.getId(), deletedAccommodationRoomType.getName());

        return ResponseEntity.ok(deletedAccommodationRoomType);
    }

}
