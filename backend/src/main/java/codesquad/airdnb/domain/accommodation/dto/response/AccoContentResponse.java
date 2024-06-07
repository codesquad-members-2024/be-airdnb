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

    private List<Long> amenities;

    private List<String> imageUrls;

    public static AccoContentResponse of(Accommodation accommodation) {
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
                .amenities(accommodation.getAmenities().stream().map(a -> a.getAmenity().getId()).toList())
                .imageUrls(accommodation.getImages().stream().map(AccoImage::getUrl).toList())
                .build();
    }
}
