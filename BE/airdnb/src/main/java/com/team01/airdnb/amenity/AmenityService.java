package com.team01.airdnb.amenity;

import org.springframework.stereotype.Service;

@Service
public class AmenityService {
    AmenityRepository amenityRepository;

    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    public void save(Amenity amenity) {
        amenityRepository.save(amenity);
    }
}
