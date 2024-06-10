package team10.airdnb.accommodation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team10.airdnb.accommodation.controller.request.AccommodationCreateRequest;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.repository.AccommodationRepository;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;
import team10.airdnb.accommodation_room_type.exception.AccommodationRoomTypeNotFoundException;
import team10.airdnb.accommodation_room_type.repository.AccommodationRoomTypeRepository;
import team10.airdnb.accommodation_type.entity.AccommodationType;
import team10.airdnb.accommodation_type.exception.AccommodationTypeNotFoundException;
import team10.airdnb.accommodation_type.repository.AccommodationTypeRepository;
import team10.airdnb.error.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationTypeRepository accommodationTypeRepository;
    private final AccommodationRoomTypeRepository accommodationRoomTypeRepository;
    private final AccommodationRepository accommodationRepository;

    public Accommodation createAccommodation(AccommodationCreateRequest request) {
        AccommodationType accommodationType = getAccommodationTypeById(request.accommodationType());

        AccommodationRoomType accommodationRoomType = getAccommodationRoomTypeById(request.accommodationRoomType());

        Accommodation accommodation = request.toEntity(accommodationType, accommodationRoomType);

        log.info("생성된 숙소 정보 : {}", accommodation);

        return accommodationRepository.save(accommodation);
    }

    private AccommodationType getAccommodationTypeById(Long accommodationTypeId) {
        if (accommodationTypeId != null) {
            return accommodationTypeRepository.findById(accommodationTypeId)
                    .orElseThrow(() -> new AccommodationTypeNotFoundException(ErrorCode.ACCOMMODATION_TYPE_NOT_EXISTS));
        }
        return null;
    }


    private AccommodationRoomType getAccommodationRoomTypeById(Long accommodationRoomTypeId) {
        if (accommodationRoomTypeId != null) {
            return accommodationRoomTypeRepository.findById(accommodationRoomTypeId)
                    .orElseThrow(() -> new AccommodationRoomTypeNotFoundException(ErrorCode.ACCOMMODATION_ROOM_TYPE_NOT_EXISTS));
        }
        return null;
    }


}
