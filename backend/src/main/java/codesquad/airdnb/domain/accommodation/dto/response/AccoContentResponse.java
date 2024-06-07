package codesquad.airdnb.domain.accommodation.dto.response;

import codesquad.airdnb.domain.accommodation.entity.AccoImage;
import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import codesquad.airdnb.domain.accommodation.dto.additionals.LocationData;
import codesquad.airdnb.domain.accommodation.dto.additionals.FloorPlanData;
import lombok.Builder;
import lombok.Getter;

import java.sql.Time;
import java.util.List;

@Builder
@Getter
public class AccoContentResponse {

    private Long id;

    private Long hostId;

    private String title;

    private String placeCategory;

    private Long price;

    private String description;

    private Time checkInTime;

    private Time checkOutTime;

    private LocationData locationData;

    private FloorPlanData floorPlanData;

    private List<String> imageUrls;

    public static AccoContentResponse of(Accommodation accommodation, List<AccoImage> accoImages) {
        return AccoContentResponse.builder()
                .id(accommodation.getId())
                .hostId(accommodation.getHost().getId())
                .title(accommodation.getTitle())
                .placeCategory(accommodation.getPlaceCategory())
                .price(accommodation.getPrice())
                .description(accommodation.getDescription())
                .checkInTime(accommodation.getCheckInTime())
                .checkOutTime(accommodation.getCheckOutTime())
                .locationData(LocationData.toResponseEmbedded(accommodation.getLocation()))
                .floorPlanData(FloorPlanData.toResponseEmbedded(accommodation.getFloorPlan()))
                .imageUrls(accoImages.stream().map(AccoImage::getUrl).toList())
                .build();
    }
}
