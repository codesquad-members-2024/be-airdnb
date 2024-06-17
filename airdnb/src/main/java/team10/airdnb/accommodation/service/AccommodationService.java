package team10.airdnb.accommodation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team10.airdnb.accommodation.controller.request.AccommodationCreateRequest;
import team10.airdnb.accommodation.controller.request.AccommodationUpdateRequest;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.exception.AccommodationIdNotFoundException;
import team10.airdnb.accommodation.repository.AccommodationRepository;
import team10.airdnb.accommodation_amenity.entity.AccommodationAmenity;
import team10.airdnb.accommodation_amenity.repository.AccommodationAmenityRepository;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;
import team10.airdnb.accommodation_room_type.exception.AccommodationRoomTypeNotFoundException;
import team10.airdnb.accommodation_room_type.repository.AccommodationRoomTypeRepository;
import team10.airdnb.accommodation_type.entity.AccommodationType;
import team10.airdnb.accommodation_type.exception.AccommodationTypeNotFoundException;
import team10.airdnb.accommodation_type.repository.AccommodationTypeRepository;
import team10.airdnb.amenity.entity.Amenity;
import team10.airdnb.amenity.repository.AmenityRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationTypeRepository accommodationTypeRepository;
    private final AccommodationRoomTypeRepository accommodationRoomTypeRepository;
    private final AccommodationRepository accommodationRepository;
    private final AccommodationAmenityRepository accommodationAmenityRepository;
    private final AmenityRepository amenityRepository;

    public List<Accommodation> getAccommodations() {
        return accommodationRepository.findAll();
    }

    public Accommodation getAccommodation(long accommodationId) {
        return getAccommodationById(accommodationId);
    }

    public Accommodation createAccommodation(AccommodationCreateRequest request) {
        AccommodationType accommodationType = getAccommodationTypeById(request.accommodationType());

        AccommodationRoomType accommodationRoomType = getAccommodationRoomTypeById(request.accommodationRoomType());

        Accommodation accommodation = request.toEntity(accommodationType, accommodationRoomType);

        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        log.info("저장된 숙소 정보 : {}", accommodation);

        List<Amenity> amenities = amenityRepository.findAllById(request.amenityIds() != null ? request.amenityIds() : Collections.emptyList());

        Set<AccommodationAmenity> accommodationAmenities = amenities.stream()
                .map(amenity -> AccommodationAmenity.builder()
                        .accommodation(accommodation)
                        .amenity(amenity)
                        .build())
                .collect(Collectors.toSet());

        accommodationAmenityRepository.saveAll(accommodationAmenities);

        return savedAccommodation;
    }

    private Accommodation getAccommodationById(Long accommodationId) {
        return accommodationRepository.findById(accommodationId)
                .orElseThrow(AccommodationIdNotFoundException::new);
    }

    private AccommodationType getAccommodationTypeById(Long accommodationTypeId) {
        if (accommodationTypeId != null) {
            return accommodationTypeRepository.findById(accommodationTypeId)
                    .orElseThrow(AccommodationTypeNotFoundException::new);
        }
        return null;
    }


    private AccommodationRoomType getAccommodationRoomTypeById(Long accommodationRoomTypeId) {
        if (accommodationRoomTypeId != null) {
            return accommodationRoomTypeRepository.findById(accommodationRoomTypeId)
                    .orElseThrow(AccommodationRoomTypeNotFoundException::new);
        }
        return null;
    }


}
