package codesquad.airdnb.domain.accommodation.controller;

import codesquad.airdnb.domain.accommodation.entity.Accommodation;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SimpleAccommodation {

    private Long id;

    private String title;

    private String thumbnailUrl;

    public static SimpleAccommodation of(Accommodation accommodation) {
        return SimpleAccommodation.builder()
                .id(accommodation.getId())
                .title(accommodation.getTitle())
                .thumbnailUrl(accommodation.getImages().get(0).getUrl())
                .build();
    }
}
