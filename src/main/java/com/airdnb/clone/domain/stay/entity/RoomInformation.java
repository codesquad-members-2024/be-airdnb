package com.airdnb.clone.domain.stay.entity;

import com.airdnb.clone.domain.common.Guest;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Embeddable
@EqualsAndHashCode
public class RoomInformation {

    @Builder.Default
    @Embedded
    private Guest guest = Guest.builder().guestCount(1).build();

    @Builder.Default
    @Column(name = "BEDROOM_COUNT")
    private Integer bedroomCount = 0;

    @Builder.Default
    @Column(name = "BED_COUNT")
    private Integer bedCount = 1;

    @Builder.Default
    @Column(name = "BATH_COUNT")
    private Integer bathCount = 0;
}
