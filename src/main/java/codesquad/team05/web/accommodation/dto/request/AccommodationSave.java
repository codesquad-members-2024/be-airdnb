package codesquad.team05.web.accommodation.dto.request;

import codesquad.team05.domain.accomodation.AccommodationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AccommodationSave {

    @NotEmpty
    private final String name;
    @NotBlank
    @Size
    private final int price;
    @NotEmpty
    private final String address;
    @NotBlank
    @Size(min = 1)
    private final int maxCapacity;
    @NotBlank
    @Size
    private final int roomCount;
    @Size
    private final int bedCount;
    private final String description;
    private final String amenity;
    @NotBlank
    private final Long hostId;
    private final List<String> hashtags;
    private final AccommodationType accommodationType;

}
