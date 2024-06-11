package com.yourbnb.accommodation.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccommodationTypeDto {
    private final Long id;
    private final String name;
}
