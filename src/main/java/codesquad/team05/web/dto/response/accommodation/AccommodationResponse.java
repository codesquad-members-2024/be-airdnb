package codesquad.team05.web.dto.response.accommodation;

import lombok.Data;

import java.util.List;

@Data
public class AccommodationResponse {

    private Long id;
    private String name;
    private int price;
    private String address;
    private int maxCapacity;
    private int roomCount;
    private int bedCount;
    private String description;
    private String amenity;
    private List<PictureDto> pictures;

}
