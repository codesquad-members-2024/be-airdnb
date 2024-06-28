package codesquad.airdnb.domain.accommodation.dto.response;

import codesquad.airdnb.domain.accommodation.dto.additionals.FilteredAcco;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FilteredAccosResponse {
    private Long accoId;

    private String title;

    private String placeCategory;

    private Integer maxGuestCount;

    private Integer maxInfantCount;

    private Integer bedroomCount;

    private Integer bathroomCount;

    private Point coordinate;

    private Long totalPrice;

    @Setter
    private List<String> amenities;

    @Setter
    private List<String> imageUrls;

    public FilteredAccosResponse(FilteredAcco filteredAcco, List<String> amenities, List<String> imageUrls) {
        this.accoId = filteredAcco.accoId();
        this.title = filteredAcco.title();
        this.placeCategory = filteredAcco.placeCategory();
        this.maxGuestCount = filteredAcco.maxGuestCount();
        this.maxInfantCount = filteredAcco.maxInfantCount();
        this.bedroomCount = filteredAcco.bedroomCount();
        this.bathroomCount = filteredAcco.bathroomCount();
        this.coordinate = filteredAcco.coordinate();
        this.totalPrice = filteredAcco.totalPrice();
        this.amenities = amenities;
        this.imageUrls = imageUrls;
    }
}
