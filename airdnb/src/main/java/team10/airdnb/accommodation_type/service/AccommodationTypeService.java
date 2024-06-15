package team10.airdnb.accommodation_type.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team10.airdnb.accommodation_type.controller.request.AccommodationTypeCreateRequest;
import team10.airdnb.accommodation_type.controller.request.AccommodationTypeUpdateRequest;
import team10.airdnb.accommodation_type.entity.AccommodationType;
import team10.airdnb.accommodation_type.exception.AccommodationTypeNameDuplicateException;
import team10.airdnb.accommodation_type.exception.AccommodationTypeNotFoundException;
import team10.airdnb.accommodation_type.repository.AccommodationTypeRepository;
import team10.airdnb.error.ErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationTypeService {

    private final AccommodationTypeRepository accommodationTypeRepository;

    public AccommodationType saveAccommodationType(AccommodationTypeCreateRequest request) {
        validateDuplicateAccommodationTypeName(request.name());

        return accommodationTypeRepository.save(request.toEntity());
    }

    public AccommodationType updateAccommodationType(long accommodationTypeId, AccommodationTypeUpdateRequest request) {
        validateDuplicateAccommodationTypeName(request.name());

        AccommodationType accommodationType = getAccommodationTypeById(accommodationTypeId);

        accommodationType.updateName(request.name());

        return accommodationTypeRepository.save(accommodationType);
    }

    public List<AccommodationType> getAllAccommodationTypes() {
        return accommodationTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public AccommodationType deleteAccommodationType(long accommodationTypeId) {
        AccommodationType accommodationType = getAccommodationTypeById(accommodationTypeId);

        accommodationTypeRepository.delete(accommodationType);

        return accommodationType;
    }

    private AccommodationType getAccommodationTypeById(long accommodationTypeId) {
        return accommodationTypeRepository.findById(accommodationTypeId)
                .orElseThrow(AccommodationTypeNotFoundException::new);
    }

    private void validateDuplicateAccommodationTypeName(String inputAccommodationTypeName) {
        accommodationTypeRepository.findByName(inputAccommodationTypeName)
                .ifPresent(accommodationType -> {
                    throw new AccommodationTypeNameDuplicateException();
                });
    }
}
