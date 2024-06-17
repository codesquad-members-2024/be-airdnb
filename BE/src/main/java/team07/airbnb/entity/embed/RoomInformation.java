package team07.airbnb.entity.embed;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Embeddable
@NoArgsConstructor
@Getter
public class RoomInformation {
    private int bedCount;
    private int bedroomCount;
    private int bathroomCount;
    @JsonProperty("private")
    private boolean isPrivate;
    private int maxOccupancy;
}
