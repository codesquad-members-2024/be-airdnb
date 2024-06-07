package codesquad.airdnb.domain.accommodation.dto.request;

import codesquad.airdnb.domain.accommodation.entity.AccoImage;
import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import codesquad.airdnb.domain.accommodation.dto.additionals.FloorPlanData;
import codesquad.airdnb.domain.accommodation.dto.additionals.LocationData;
import codesquad.airdnb.domain.member.Member;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccoCreateRequest {

    @NotNull
    private Long hostId;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    private String placeCategory;

    @NotNull
    private Long price;

    @Size(max = 500)
    private String description;

    @NotNull
    private Time checkInTime;

    @NotNull
    private Time checkOutTime;

    @Valid
    private LocationData locationData;

    @Valid
    private FloorPlanData floorPlanData;

    @Size(min = 5)
    private List<String> imageUrls;

    private List<Long> amenities;

    public boolean validateImageUrls() {
        return imageUrls.stream().noneMatch(String::isBlank);
    }

    public Accommodation buildAccommodation(Member host) {
        return Accommodation.builder()
                .host(host)
                .title(title)
                .placeCategory(placeCategory)
                .price(price)
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
