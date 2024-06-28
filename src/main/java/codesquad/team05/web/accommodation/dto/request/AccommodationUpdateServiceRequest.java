package codesquad.team05.web.accommodation.dto.request;

import codesquad.team05.domain.accommodation.AccommodationType;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Getter
public class AccommodationUpdateServiceRequest {

    private final String name;
    private final int price;
    private final String address;
    private final int maxCapacity;
    private final int roomCount;
    private final int bedCount;
    private final String description;
    private final String amenity;
    private final List<MultipartFile> files;
    private final AccommodationType accommodationType;
    private final List<String> hashtagContents;

    public AccommodationUpdateServiceRequest(
            String name,
            int price,
            String address,
            int maxCapacity,
            int roomCount,
            int bedCount,
            String description,
            String amenity,
            List<MultipartFile> files,
            AccommodationType accommodationType,
            List<String> hashtagContents) {
        this.name = name;
        this.price = price;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.roomCount = roomCount;
        this.bedCount = bedCount;
        this.description = description;
        this.amenity = amenity;
        this.files = Optional.ofNullable(files).orElse(List.of());
        this.accommodationType = accommodationType;
        this.hashtagContents = hashtagContents;
    }
}
