package team07.airbnb.domain.accommodation.property;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class RoomInformation {
    private int bedCount;
    private int bedroomCount;
    private int bathroomCount;
    private boolean isPrivate;
    private int maxOccupancy;
}
