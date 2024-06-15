package team07.airbnb.entity.embed;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

@ToString
@Embeddable
@Getter
public class RoomInformation {
    private int bedCount;
    private int bedroomCount;
    private int bathroomCount;
    private boolean isPrivate;
    private int maxOccupancy;
}
