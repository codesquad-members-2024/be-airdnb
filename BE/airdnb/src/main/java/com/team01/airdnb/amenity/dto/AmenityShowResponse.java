package com.team01.airdnb.amenity.dto;

import com.team01.airdnb.amenity.AmenityStatus;
import lombok.Builder;

@Builder
public record AmenityShowResponse(
    Integer beds,
    Integer bathrooms,
    AmenityStatus kitchen,
    AmenityStatus dedicated_workspace,
    AmenityStatus tv,
    AmenityStatus washing_machine,
    AmenityStatus air_conditioning,
    AmenityStatus wireless_internet,
    AmenityStatus free_parking,
    AmenityStatus paid_parking
) {

}
