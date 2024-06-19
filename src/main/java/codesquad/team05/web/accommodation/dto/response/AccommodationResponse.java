package codesquad.team05.web.accommodation.dto.response;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
public class AccommodationResponse {

    private final Long id;
    private final String name;
    private final int price;
    private final String address;
    private final int maxCapacity;
    private final int roomCount;
    private final int bedCount;
    private final String description;
    private final String amenity;
    @Setter
    private List<PictureDto> pictures;
}
