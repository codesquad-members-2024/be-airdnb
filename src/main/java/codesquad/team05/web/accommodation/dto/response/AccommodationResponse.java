package codesquad.team05.web.accommodation.dto.response;

import codesquad.team05.web.picture.dto.response.PictureDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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
    private boolean isOnSale;
    private Double discountRate;
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate endDate;

}
