package codesquad.airdnb.domain.accommodation.dto.request;

import codesquad.airdnb.domain.accommodation.dto.additionals.FloorPlanData;
import codesquad.airdnb.domain.accommodation.dto.additionals.LocationData;
import codesquad.airdnb.domain.accommodation.entity.AccoImage;
import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import codesquad.airdnb.domain.member.Member;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.sql.Time;
import java.util.List;

@Builder
public record AccoCreateRequest (
        @NotNull
        Long hostId,

        @NotBlank
        @Size(max = 50)
        String title,

        @NotBlank
        String placeCategory,

        @NotNull
        @Size(min = 1, max = 14000000)
        Long basePricePerNight,

        @Size(max = 500)
        String description,

        @NotNull
        Time checkInTime,

        @NotNull
        Time checkOutTime,

        @Valid
        LocationData locationData,

        @Valid
        FloorPlanData floorPlanData,

        @Size(min = 5)
        List<String> imageUrls,

        List<Long> amenities
) {
    public boolean validateImageUrls() {
        return imageUrls.stream().noneMatch(String::isBlank);
    }

    public Accommodation buildAccommodation(Member host) {
        return Accommodation.builder()
                .host(host)
                .title(title)
                .placeCategory(placeCategory)
                .basePricePerNight(basePricePerNight)
                .description(description)
                .checkInTime(checkInTime)
                .checkOutTime(checkOutTime)
                .floorPlan(floorPlanData.toEmbedded())
                .location(locationData.toEmbedded())
                .build();
    }

    public List<AccoImage> buildAccoImages(Accommodation accommodation) {
        return imageUrls.stream()
                .map(url -> AccoImage.builder()
                                .url(url)
                                .accommodation(accommodation)
                                .build()
                ).toList();

    }
}
