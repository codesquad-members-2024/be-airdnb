package codesquad.team05.web.accommodation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class AccommodationSave {

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
    @NotBlank
    private Long hostId;
    private List<String> hashtags;
}
