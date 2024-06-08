package team10.airdnb.accommodation_room_type.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team10.airdnb.accommodation_room_type.controller.request.AccommodationRoomTypeCreateRequest;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;
import team10.airdnb.accommodation_room_type.exception.AccommodationRoomTypeNameDuplicateException;
import team10.airdnb.accommodation_room_type.repository.AccommodationRoomTypeRepository;
import team10.airdnb.error.ErrorCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccommodationRoomTypeService {

    private final AccommodationRoomTypeRepository accommodationRoomTypeRepository;

    public AccommodationRoomType saveAccommodationRoomType(AccommodationRoomTypeCreateRequest request) {
        validateDuplicateRoomTypeName(request.name());

        return accommodationRoomTypeRepository.save(request.toEntity());
    }

    private void validateDuplicateRoomTypeName(String inputRoomTypeName) {
        accommodationRoomTypeRepository.findByName(inputRoomTypeName)
                .ifPresent(roomType -> {
                    throw new AccommodationRoomTypeNameDuplicateException(ErrorCode.ALREADY_SAVED_ROOM_TYPE);
                });
    }
}
