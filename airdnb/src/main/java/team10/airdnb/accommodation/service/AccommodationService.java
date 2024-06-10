package team10.airdnb.accommodation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team10.airdnb.accommodation.controller.request.AccommodationCreateRequest;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.repository.AccommodationRepository;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;
import team10.airdnb.accommodation_room_type.service.AccommodationRoomTypeService;
import team10.airdnb.accommodation_type.entity.AccommodationType;
import team10.airdnb.accommodation_type.service.AccommodationTypeService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationTypeService accommodationTypeService;
    private final AccommodationRoomTypeService accommodationRoomTypeService;
    private final AccommodationRepository accommodationRepository;

    public Accommodation createAccommodation(AccommodationCreateRequest request) {
        AccommodationType accommodationType = getAccommodationTypeById(request.accommodationType());

        AccommodationRoomType accommodationRoomType = getAccommodationRoomTypeById(request.accommodationRoomType());

        Accommodation accommodation = request.toEntity(accommodationType, accommodationRoomType);

        log.info("생성된 숙소 정보 : {}", accommodation);

        return accommodationRepository.save(accommodation);
    }

    private AccommodationType getAccommodationTypeById(Long accommodationTypeId) {
        return accommodationTypeId == null ? null : accommodationTypeService.getAccommodationTypeById(accommodationTypeId);
    }


    private AccommodationRoomType getAccommodationRoomTypeById(Long accommodationRoomTypeId) {
        return accommodationRoomTypeId == null ? null : accommodationRoomTypeService.getAccommodationRoomTypeById(accommodationRoomTypeId);
    }


}
