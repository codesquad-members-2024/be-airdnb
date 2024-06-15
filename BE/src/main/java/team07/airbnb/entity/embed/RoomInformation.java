package team07.airbnb.entity.embed;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class RoomInformation {
    private int bedCount;
    private int bedroomCount;
    private int bathroomCount;
    private boolean isPrivate;
    private int maxOccupancy;
}
