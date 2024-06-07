package codesquad.airdnb.domain.accommodation.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.*;

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
