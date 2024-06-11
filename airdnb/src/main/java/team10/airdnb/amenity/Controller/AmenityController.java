package team10.airdnb.amenity.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.airdnb.amenity.Service.AmenityService;
import team10.airdnb.amenity.entity.Amenity;

import java.util.List;

@RestController
@RequestMapping("/amenities")
public class AmenityController {
    private final AmenityService amenityService;

    @Autowired
    public AmenityController(AmenityService amenityService) {
        this.amenityService = amenityService;
    }

    // 새로운 어메니티 추가 (POST)
    @PostMapping
    public ResponseEntity<Amenity> createAmenity(@RequestBody Amenity amenity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(amenityService.createAmenity(amenity));
    }

    // 특정 숙박 시설에 어메니티 추가 (PUT)
    @PutMapping("/accommodations/{accommodationId}/amenities")
    public ResponseEntity<Void> addAmenitiesToAccommodation(@PathVariable Long accommodationId, @RequestBody List<Long> amenityIds) {
        amenityService.addAmenitiesToAccommodation(accommodationId, amenityIds);
        return ResponseEntity.ok().build();
    }
}
