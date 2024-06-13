package codesquad.airdnb.domain.accommodation.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FloorPlan {

    @Column(name = "MAX_GUEST_COUNT")
    private Integer maxGuestCount;

    @Column(name = "MAX_INFANT_COUNT")
    private Integer maxInfantCount;

    @Column(name = "BEDROOM_COUNT")
    private Integer bedroomCount;

    @Column(name = "BED_COUNT")
    private Integer bedCount;

    @Column(name = "BATHROOM_COUNT")
    private Integer bathroomCount;
}
