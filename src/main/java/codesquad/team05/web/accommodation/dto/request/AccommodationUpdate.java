package codesquad.team05.web.accommodation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccommodationUpdate {

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
}
