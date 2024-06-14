package com.yourbnb.accommodation.model.dto;

import com.yourbnb.image.dto.AccommodationImageDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccommodationResponse {
    private final Long id;
    private final String name;
    private final String phoneNumber;
    private final String address;
    private final String longitude;
    private final String latitude;
    private final Integer maxCapacity;
    private final Integer cleaningFee;
    private final Integer price;
    private final String roomType;
    private final String hostId;
    private final AccommodationTypeDto accommodationType;
    private final AccommodationImageDto accommodationImage;
    private final List<AccommodationAmenityDto> accommodationAmenities;
}
