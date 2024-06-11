package team10.airdnb.accommodation_room_type.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team10.airdnb.accommodation_room_type.controller.request.AccommodationRoomTypeCreateRequest;
import team10.airdnb.accommodation_room_type.controller.request.AccommodationRoomTypeUpdateRequest;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;
import team10.airdnb.accommodation_room_type.exception.AccommodationRoomTypeNameDuplicateException;
import team10.airdnb.accommodation_room_type.exception.AccommodationRoomTypeNotFoundException;
import team10.airdnb.accommodation_room_type.repository.AccommodationRoomTypeRepository;
import team10.airdnb.error.ErrorCode;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccommodationRoomTypeService {

    private final AccommodationRoomTypeRepository accommodationRoomTypeRepository;

    public AccommodationRoomType saveAccommodationRoomType(AccommodationRoomTypeCreateRequest request) {
        validateDuplicateRoomTypeName(request.name());

        return accommodationRoomTypeRepository.save(request.toEntity());
    }

    public AccommodationRoomType updateAccommodationRoomType(long accommodationRoomTypeId, AccommodationRoomTypeUpdateRequest request) {
        validateDuplicateRoomTypeName(request.name());

        AccommodationRoomType accommodationRoomType = getAccommodationRoomTypeById(accommodationRoomTypeId);

        accommodationRoomType.updateName(request.name());

        return accommodationRoomTypeRepository.save(accommodationRoomType);
    }

    public List<AccommodationRoomType> getAllAccommodationRoomTypes() {
        return accommodationRoomTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public AccommodationRoomType deleteAccommodationRoomType(long accommodationRoomTypeId) {
        AccommodationRoomType accommodationRoomType = getAccommodationRoomTypeById(accommodationRoomTypeId);

        accommodationRoomTypeRepository.delete(accommodationRoomType);

        return accommodationRoomType;
    }


    private AccommodationRoomType getAccommodationRoomTypeById(long accommodationRoomTypeId) {
        return accommodationRoomTypeRepository.findById(accommodationRoomTypeId)
                .orElseThrow(() -> new AccommodationRoomTypeNotFoundException(ErrorCode.ACCOMMODATION_ROOM_TYPE_NOT_EXISTS));
    }

    private void validateDuplicateRoomTypeName(String inputRoomTypeName) {
        accommodationRoomTypeRepository.findByName(inputRoomTypeName)
                .ifPresent(roomType -> {
                    throw new AccommodationRoomTypeNameDuplicateException(ErrorCode.ALREADY_SAVED_ROOM_TYPE);
                });
    }
}
