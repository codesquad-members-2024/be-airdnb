package codesquad.team05.util;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.web.accommodation.dto.request.AccommodationSave;
import codesquad.team05.web.accommodation.dto.request.AccommodationSaveServiceRequest;
import codesquad.team05.web.accommodation.dto.request.AccommodationUpdate;
import codesquad.team05.web.accommodation.dto.request.AccommodationUpdateServiceRequest;
import codesquad.team05.web.accommodation.dto.response.AccommodationResponse;

public class AccommodationMapper {

    public static AccommodationSaveServiceRequest toSaveService(AccommodationSave saveRequest) {
        return new AccommodationSaveServiceRequest(
                saveRequest.getName(),
                saveRequest.getPrice(),
                saveRequest.getAddress(),
                saveRequest.getMaxCapacity(),
                saveRequest.getRoomCount(),
                saveRequest.getBedCount(),
                saveRequest.getDescription(),
                saveRequest.getAmenity(),
                saveRequest.getHostId()
        );
    }

    public static AccommodationUpdateServiceRequest toUpdateService(AccommodationUpdate updateRequest) {
        return new AccommodationUpdateServiceRequest(
                updateRequest.getName(),
                updateRequest.getPrice(),
                updateRequest.getAddress(),
                updateRequest.getMaxCapacity(),
                updateRequest.getRoomCount(),
                updateRequest.getBedCount(),
                updateRequest.getDescription(),
                updateRequest.getAmenity()
        );
    }

    public static Accommodation toEntity(AccommodationSaveServiceRequest serviceRequest) {
        return new Accommodation(
                serviceRequest.getName(),
                serviceRequest.getPrice(),
                serviceRequest.getAddress(),
                serviceRequest.getMaxCapacity(),
                serviceRequest.getRoomCount(),
                serviceRequest.getBedCount(),
                serviceRequest.getDescription(),
                serviceRequest.getAmenity(),
                serviceRequest.getHostId()
        );
    }

    public static AccommodationResponse toResponse(Accommodation accommodation) {
        // setPictures(pictures.stream().map(Picture::toEntity).toList())
        return new AccommodationResponse(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getPrice(),
                accommodation.getAddress(),
                accommodation.getMaxCapacity(),
                accommodation.getRoomCount(),
                accommodation.getBedCount(),
                accommodation.getDescription(),
                accommodation.getAmenity()
        );
    }
}
