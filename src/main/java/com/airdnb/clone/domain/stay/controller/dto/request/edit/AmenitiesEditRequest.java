package com.airdnb.clone.domain.stay.controller.dto.request.edit;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AmenitiesEditRequest {

    @NotEmpty
    private final List<Long> amenityIds;
}
