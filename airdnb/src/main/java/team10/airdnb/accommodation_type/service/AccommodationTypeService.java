package team10.airdnb.accommodation_type.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team10.airdnb.accommodation_type.controller.request.AccommodationTypeCreateRequest;
import team10.airdnb.accommodation_type.entity.AccommodationType;
import team10.airdnb.accommodation_type.exception.AccommodationTypeNameDuplicateException;
import team10.airdnb.accommodation_type.repository.AccommodationTypeRepository;
import team10.airdnb.error.ErrorCode;

@Service
@RequiredArgsConstructor
public class AccommodationTypeService {

    private final AccommodationTypeRepository accommodationTypeRepository;

    public AccommodationType saveAccommodationType(AccommodationTypeCreateRequest request) {
        validateDuplicateAccommodationTypeName(request.name());

        return accommodationTypeRepository.save(request.toEntity());
    }

    private void validateDuplicateAccommodationTypeName(String inputAccommodationTypeName) {
        accommodationTypeRepository.findByName(inputAccommodationTypeName)
                .ifPresent(accommodationType -> {
                    throw new AccommodationTypeNameDuplicateException(ErrorCode.ALREADY_SAVED_ACCOMMODATION_TYPE);
                });
    }
}
