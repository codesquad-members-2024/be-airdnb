package team10.airdnb.accommodation_amenity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation_amenity.dto.AccommodationAmenityDto;
import team10.airdnb.accommodation_amenity.entity.AccommodationAmenity;
import team10.airdnb.accommodation_amenity.repository.AccommodationAmenityRepository;
import team10.airdnb.amenity.entity.Amenity;
import team10.airdnb.amenity.repository.AmenityRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccommodationAmenityService {

    private final AccommodationAmenityRepository accommodationAmenityRepository;
    private final AmenityRepository amenityRepository;

    public AccommodationAmenityDto saveAccommodationAmenity(Accommodation savedAccommodation, List<Long> amenityIds) {
        List<Amenity> amenities = amenityRepository.findAllById(amenityIds != null ? amenityIds : Collections.emptyList());

        Set<AccommodationAmenity> accommodationAmenities = amenities.stream()
                .map(amenity -> AccommodationAmenity.builder()
                        .accommodation(savedAccommodation)
                        .amenity(amenity)
                        .build())
                .collect(Collectors.toSet());

        log.info("숙소 Id : #{}에 저장된 편의시설 : #{}", savedAccommodation.getId(), accommodationAmenities.toString());

        accommodationAmenityRepository.saveAll(accommodationAmenities);

        return AccommodationAmenityDto.from(accommodationAmenities);
    }

}
