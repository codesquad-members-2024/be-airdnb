package team10.airdnb.accommodation_room_type.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team10.airdnb.accommodation_room_type.controller.request.AccommodationRoomTypeCreateRequest;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;
import team10.airdnb.accommodation_room_type.service.AccommodationRoomTypeService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/room-type")
public class AccommodationRoomTypeRestController {

    private final AccommodationRoomTypeService accommodationRoomTypeService;

    @PostMapping
    public ResponseEntity<?> saveAccommodationRoomType(@RequestBody @Valid AccommodationRoomTypeCreateRequest request) {
        AccommodationRoomType response = accommodationRoomTypeService.saveAccommodationRoomType(request);

        log.info("숙소 Room Type 저장 완료, # {} :  저장된 이름 : {}", response.getId(), response.getName());

        return ResponseEntity.ok(response);
    }

}
