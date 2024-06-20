package team03.airdnb.accommodationAmenity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.AccommodationRepository;
import team03.airdnb.amenity.Amenity;
import team03.airdnb.amenity.AmenityRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccommodationAmenityService {

    private final AccommodationAmenityRepository accommodationAmenityRepository;
    private final AccommodationRepository accommodationRepository;
    private final AmenityRepository amenityRepository;

    public void createAccommodationAmenity(List<Long> amenityIds, Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).get();
        List<AccommodationAmenity> accommodationAmenities = amenityIds.stream()
                .map(amenityId -> {
                    Amenity amenity = amenityRepository.findById(amenityId).get();
                    return AccommodationAmenity.builder()
                            .accommodation(accommodation)
                            .amenity(amenity)
                            .build();
                })
                .collect(Collectors.toList());

        accommodationAmenityRepository.saveAll(accommodationAmenities);
    }

    public void updateAccommodationAmenity(List<Long> amenityIds, Long accommodationId) {
        accommodationAmenityRepository.deleteByAccommodationId(accommodationId);

        Accommodation accommodation = accommodationRepository.findById(accommodationId).get();
        List<AccommodationAmenity> accommodationAmenities = amenityIds.stream()
                .map(amenityId -> {
                    Amenity amenity = amenityRepository.findById(amenityId).get();
                    return AccommodationAmenity.builder()
                            .accommodation(accommodation)
                            .amenity(amenity)
                            .build();
                })
                .collect(Collectors.toList());

        accommodationAmenityRepository.saveAll(accommodationAmenities);
    }
}
