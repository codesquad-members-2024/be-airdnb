package codesquad.airdnb.domain.accommodation.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FloorPlan {

    private Integer maxGuest;

    private Integer maxInfant;

    private Integer bedroomNum;

    private Integer bedNum;

    private Integer bathroomNum;
}
