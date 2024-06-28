package codesquad.team05.util;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.web.accommodation.dto.request.AccommodationSave;
import codesquad.team05.web.accommodation.dto.request.AccommodationSaveServiceRequest;
import codesquad.team05.web.accommodation.dto.request.AccommodationUpdate;
import codesquad.team05.web.accommodation.dto.request.AccommodationUpdateServiceRequest;
import codesquad.team05.web.accommodation.dto.response.AccommodationResponse;
import codesquad.team05.web.accommodation.dto.response.PictureDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class AccommodationMapper {

    public static AccommodationSaveServiceRequest toSaveService(AccommodationSave saveRequest, List<MultipartFile> files) {
        AccommodationSaveServiceRequest accommodationSaveServiceRequest = new AccommodationSaveServiceRequest(
                saveRequest.getName(),
                saveRequest.getPrice(),
                saveRequest.getAddress(),
                saveRequest.getMaxCapacity(),
                saveRequest.getRoomCount(),
                saveRequest.getBedCount(),
                saveRequest.getDescription(),
                saveRequest.getAmenity(),
                saveRequest.getAccommodationType(),
                saveRequest.getHashtagContents()
        );
        files.forEach(
                file -> accommodationSaveServiceRequest.getFiles().add(file)
        );
        return accommodationSaveServiceRequest;
    }

    public static AccommodationUpdateServiceRequest toUpdateService(AccommodationUpdate updateRequest, List<MultipartFile> files) {
        AccommodationUpdateServiceRequest accommodationUpdateServiceRequest = new AccommodationUpdateServiceRequest(
                updateRequest.getName(),
                updateRequest.getPrice(),
                updateRequest.getAddress(),
                updateRequest.getMaxCapacity(),
                updateRequest.getRoomCount(),
                updateRequest.getBedCount(),
                updateRequest.getDescription(),
                updateRequest.getAmenity(),
                updateRequest.getAccommodationType(),
                updateRequest.getHashtagContents()
        );
        files.forEach(
                file -> accommodationUpdateServiceRequest.getFiles().add(file)
        );
        return accommodationUpdateServiceRequest;
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
                serviceRequest.getAccommodationType()
        );
    }

    public static AccommodationResponse toResponse(Accommodation accommodation) {
        return new AccommodationResponse(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getPrice(),
                accommodation.getAddress(),
                accommodation.getMaxCapacity(),
                accommodation.getRoomCount(),
                accommodation.getBedCount(),
                accommodation.getDescription(),
                accommodation.getAmenity(),
                accommodation.getAccommodationType(),
                accommodation.getPictures().stream()
                        .map(picture -> new PictureDto(picture.getUrl()))
                        .toList()
        );
    }
}
