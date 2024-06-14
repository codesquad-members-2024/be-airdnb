package codesquad.airdnb.domain.accommodation.dto.response;

import codesquad.airdnb.domain.accommodation.entity.AccoImage;
import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import codesquad.airdnb.domain.accommodation.dto.additionals.LocationData;
import codesquad.airdnb.domain.accommodation.dto.additionals.FloorPlanData;
import lombok.Builder;

import java.sql.Time;
import java.util.List;

@Builder
public record AccoContentResponse (
        Long id,

        Long hostId,

        String title,

        String placeCategory,

        Long basePricePerNight,

        String description,

        Time checkInTime,

        Time checkOutTime,

        LocationData locationData,

        FloorPlanData floorPlanData,

        List<Long> amenities,

        List<String> imageUrls
) {
    public static AccoContentResponse of(Accommodation accommodation) {
        return AccoContentResponse.builder()
                .id(accommodation.getId())
                .hostId(accommodation.getHost().getId())
                .title(accommodation.getTitle())
                .placeCategory(accommodation.getPlaceCategory())
                .basePricePerNight(accommodation.getBasePricePerNight())
                .description(accommodation.getDescription())
                .checkInTime(accommodation.getCheckInTime())
                .checkOutTime(accommodation.getCheckOutTime())
                .locationData(LocationData.of(accommodation.getLocation()))
                .floorPlanData(FloorPlanData.of(accommodation.getFloorPlan()))
                .amenities(accommodation.getAmenities().stream().map(a -> a.getAmenity().getId()).toList())
                .imageUrls(accommodation.getImages().stream().map(AccoImage::getUrl).toList())
                .build();
    }
}
