package com.yourbnb.accommodation.service;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.accommodation.model.Amenity;
import com.yourbnb.accommodation.model.dto.AccommodationResponse;
import com.yourbnb.accommodation.repository.AccommodationAmenityRepository;
import com.yourbnb.accommodation.repository.AccommodationRepository;
import com.yourbnb.accommodation.repository.AmenityRepository;
import com.yourbnb.accommodation.util.AccommodationMapper;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccommodationService {
    private final AccommodationRepository accommodationRepository;
    private final AmenityRepository amenityRepository;
    private final AccommodationAmenityRepository accommodationAmenityRepository;


    /**
     * 전체 숙소 리스트를 반환한다.
     */
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public List<AccommodationResponse> getAccommodations() {
        List<AccommodationResponse> accommodationResponses = accommodationRepository.findAll().stream()
                .map(this::mapAccommodationToResponse)
                .collect(Collectors.toList());

        log.info("총 {}개의 숙소 탐색 성공", accommodationResponses.size());
        return accommodationResponses;
    }

    private AccommodationResponse mapAccommodationToResponse(Accommodation accommodation) {
        Set<Long> amenityIds = accommodationAmenityRepository.findAmenitiesByAccommodationId(accommodation.getId());
        Set<Amenity> amenities = amenityRepository.findAllByIdIsIn(amenityIds);
        return AccommodationMapper.toAccommodationResponse(accommodation, amenities);
    }
}
