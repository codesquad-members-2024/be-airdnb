package codesquad.airdnb.domain.accommodation.dto.response;

import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
public record SimpleAccommodationResponse (
    Long id,

    String title,

    String thumbnailUrl
) {

    public static SimpleAccommodationResponse of(Accommodation accommodation) {
        return SimpleAccommodationResponse.builder()
                .id(accommodation.getId())
                .title(accommodation.getTitle())
                .thumbnailUrl(accommodation.getImages().get(0).getUrl())
                .build();
    }
}
