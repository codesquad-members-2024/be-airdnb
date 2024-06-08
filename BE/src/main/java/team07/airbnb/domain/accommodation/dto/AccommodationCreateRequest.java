package team07.airbnb.domain.accommodation.dto;

import team07.airbnb.domain.accommodation.entity.AccommodationEntity;
import team07.airbnb.domain.accommodation.property.AccommodationLocation;
import team07.airbnb.domain.accommodation.property.AccommodationType;
import team07.airbnb.domain.accommodation.property.RoomInformation;
import team07.airbnb.domain.user.entity.UserEntity;

import java.util.List;

public record AccommodationCreateRequest(
        AccommodationType type,
        RoomInformation roomInformation,
        AccommodationLocation address,
        String name,
        String description,
        List<String> pictures,
        int basePricePerDay
) {
    public AccommodationEntity toEntity(UserEntity host){
        AccommodationEntity newAccommodation = AccommodationEntity.builder()
                .host(host)
                .type(type)
                .roomInformation(roomInformation)
                .address(address)
                .name(name)
                .description(description)
                .basePricePerDay(basePricePerDay)
                .build();

        pictures.forEach(newAccommodation::addPicture);

        return newAccommodation;
    }
}
