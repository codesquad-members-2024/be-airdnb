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
public class Amenities {

    private boolean hasWifi;

    private boolean hasTv;

    private boolean hasKitchen;

    private boolean hasWasher;

    private boolean hasFreeParking;

    private boolean hasAirConditioning;

    private boolean hasWorkspace;

    private boolean hasPool;

    private boolean hasBbq;

    private boolean hasGym;
}
