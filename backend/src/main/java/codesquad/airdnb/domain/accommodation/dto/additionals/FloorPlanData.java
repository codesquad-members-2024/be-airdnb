package codesquad.airdnb.domain.accommodation.dto.additionals;

import codesquad.airdnb.domain.accommodation.entity.embedded.FloorPlan;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FloorPlanData {

    @NotNull
    @Min(value = 1)
    private Integer maxGuest;

    @NotNull
    @Min(value = 0)
    private Integer maxInfant;

    @NotNull
    @Min(value = 0)
    private Integer bedroomNum;

    @NotNull
    @Min(value = 1)
    private Integer bedNum;

    @NotNull
    @Min(value = 0)
    private Integer bathroomNum;

    public FloorPlan toEmbedded() {
        return FloorPlan.builder()
                .maxGuest(maxGuest)
                .maxInfant(maxInfant)
                .bedroomNum(bedroomNum)
                .bedNum(bedNum)
                .bathroomNum(bathroomNum)
                .build();
    }

    public static FloorPlanData toResponseEmbedded(FloorPlan floorPlan) {
        return FloorPlanData.builder()
                .maxGuest(floorPlan.getMaxGuest())
                .maxInfant(floorPlan.getMaxInfant())
                .bedroomNum(floorPlan.getBedroomNum())
                .bedNum(floorPlan.getBedNum())
                .bathroomNum(floorPlan.getBathroomNum())
                .build();
    }
}
