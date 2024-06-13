package codesquad.airdnb.domain.accommodation.dto.response;

import codesquad.airdnb.domain.accommodation.entity.AccoImage;
import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import codesquad.airdnb.domain.accommodation.entity.Amenity;
import org.locationtech.jts.geom.Point;

import java.sql.Time;
import java.util.List;

public record DetailsForReservationResponse(
    String title,
    String description,
    Integer maxGuestCount,
    Integer maxInfantCount,
    Integer bedroomCount,
    Integer bedCount,
    Integer bathroomCount,
    Time checkInTime,
    Time checkOutTime,
    Point coordinate,
    List<AccoImage> imageUrls,
    List<Amenity> amenities,
    Long totalPrice
) {
    public static DetailsForReservationResponse of(Accommodation accommodation, List<Amenity> amenities, Long totalPrice) {
        return new DetailsForReservationResponse(
                accommodation.getTitle(),
                accommodation.getDescription(),
                accommodation.getFloorPlan().getMaxGuestCount(),
                accommodation.getFloorPlan().getMaxInfantCount(),
                accommodation.getFloorPlan().getBedroomCount(),
                accommodation.getFloorPlan().getBedCount(),
                accommodation.getFloorPlan().getBathroomCount(),
                accommodation.getCheckInTime(),
                accommodation.getCheckOutTime(),
                accommodation.getLocation().getCoordinate(),
                accommodation.getImages(),
                amenities,
                totalPrice);
    }
}
