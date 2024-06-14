package com.team01.airdnb.amenity;

import com.team01.airdnb.amenity.dto.AmenityShowResponse;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class AmenityService {
    AmenityRepository amenityRepository;

    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    public Amenity findAmenityById(Long id) {
        return amenityRepository.findByAccommodationId(id)
            .orElseThrow(() -> new NoSuchElementException("해당하는 어메니티가 존재하지 않습니다"));
    }

    public AmenityShowResponse findByAccommodationId(Long id) {
        Amenity amenity = findAmenityById(id);

        return AmenityShowResponse.builder()
            .beds(amenity.getBeds())
            .bathrooms(amenity.getBathrooms())
            .kitchen(amenity.getKitchen())
            .dedicated_workspace(amenity.getDedicatedWorkspace())
            .tv(amenity.getTv())
            .washing_machine(amenity.getWashingMachine())
            .air_conditioning(amenity.getAirConditioner())
            .washing_machine(amenity.getWashingMachine())
            .wireless_internet(amenity.getWirelessInternet())
            .free_parking(amenity.getFreeParking())
            .paid_parking(amenity.getPaidParking())
            .build();
    }
}
