package codesquad.team05.web.accommodation.dto.request;

import codesquad.team05.domain.accommodation.AccommodationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

@Getter
public class AccommodationUpdate {

    @NotEmpty
    private final String name;

    @NotBlank
    @Positive
    private final int price;

    @NotEmpty
    private final String address;

    @NotBlank
    @Positive
    private final int maxCapacity;

    @NotBlank
    @Positive
    private final int roomCount;

    @NotBlank
    @Positive
    private final int bedCount;

    private final String description;
    private final String amenity;

    @NotBlank
    private final AccommodationType accommodationType;
    private final List<String> hashtagContents;

    public AccommodationUpdate(
            String name,
            int price,
            String address,
            int maxCapacity,
            int roomCount,
            int bedCount,
            String description,
            String amenity,
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
        this.accommodationType = accommodationType;
        this.hashtagContents = Optional.ofNullable(hashtagContents).orElse(List.of());
    }
}
