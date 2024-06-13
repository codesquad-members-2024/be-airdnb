package com.airdnb.clone.domain.stay.controller.dto.request.edit;

import com.airdnb.clone.domain.stay.entity.StayImage;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ImagesEditRequest {

    @NotEmpty
    private final List<StayImage> images;
}
