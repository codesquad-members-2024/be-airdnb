package com.airdnb.clone.domain.stay.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Amenity {

    private Long id;
    private Stay stay;
    private Category category;
    private String name;

    public enum Category {
        BATH_ROOM, BED_ROOM, LAUNDRY, KITCHEN, OTHER
    }
}
