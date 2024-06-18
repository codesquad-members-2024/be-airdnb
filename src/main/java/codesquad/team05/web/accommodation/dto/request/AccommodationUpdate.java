package codesquad.team05.web.accommodation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class AccommodationUpdate {

    @NotEmpty
    private String name;
    @NotBlank
    @Size
    private int price;
    @NotEmpty
    private String address;
    @NotBlank
    @Size(min = 1)
    private int maxCapacity;
    @NotBlank
    @Size
    private int roomCount;
    @Size
    private int bedCount;
    private String description;
    private String amenity;
    private List<String> hashtags;

}
