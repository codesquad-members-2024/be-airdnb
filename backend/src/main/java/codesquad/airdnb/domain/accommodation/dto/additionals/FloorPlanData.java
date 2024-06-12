package codesquad.airdnb.domain.accommodation.dto.additionals;

import codesquad.airdnb.domain.accommodation.entity.embedded.FloorPlan;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
public record FloorPlanData (
        @NotNull
        @Min(value = 1)
        Integer maxGuestCount,

        @NotNull
        @Min(value = 0)
        Integer maxInfantCount,

        @NotNull
        @Min(value = 0)
        Integer bedroomCount,

        @NotNull
        @Min(value = 1)
        Integer bedCount,

        @NotNull
        @Min(value = 0)
        Integer bathroomCount
) {
    public FloorPlan toEmbedded() {
        return FloorPlan.builder()
                .maxGuestCount(maxGuestCount)
                .maxInfantCount(maxInfantCount)
                .bedroomCount(bedroomCount)
                .bedCount(bedCount)
                .bathroomCount(bathroomCount)
                .build();
    }

    public static FloorPlanData toResponseEmbedded(FloorPlan floorPlan) {
        return FloorPlanData.builder()
                .maxGuestCount(floorPlan.getMaxGuestCount())
                .maxInfantCount(floorPlan.getMaxInfantCount())
                .bedroomCount(floorPlan.getBedroomCount())
                .bedCount(floorPlan.getBedCount())
                .bathroomCount(floorPlan.getBathroomCount())
                .build();
    }
}
