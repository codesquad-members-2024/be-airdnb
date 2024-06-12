package codesquad.airdnb.domain.accommodation.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FloorPlan {

    private Integer maxGuestCount;

    private Integer maxInfantCount;

    private Integer bedroomCount;

    private Integer bedCount;

    private Integer bathroomCount;
}
