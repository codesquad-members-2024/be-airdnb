package codesquad.airdnb.domain.accommodation.dto.additionals;

import org.locationtech.jts.geom.Point;

public record FilteredAcco (
    Long accoId,

    String title,

    String placeCategory,

    Integer maxGuestCount,

    Integer maxInfantCount,

    Integer bedroomCount,

    Integer bathroomCount,

    Point coordinate,

    Long totalPrice
){
}
