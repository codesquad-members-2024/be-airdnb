package com.airdnb.clone.domain.stay.controller.dto.response.edit;

import com.airdnb.clone.domain.stay.entity.StayImage;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImagesEditResponse {

    private final List<StayImage> images;

    public static ImagesEditResponse of(List<StayImage> images) {
        return new ImagesEditResponse(images);
    }
}
