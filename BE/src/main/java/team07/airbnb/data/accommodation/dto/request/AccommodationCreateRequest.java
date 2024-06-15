package team07.airbnb.data.accommodation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import team07.airbnb.data.accommodation.enums.AccommodationType;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.entity.embed.RoomInformation;

import java.util.ArrayList;
import java.util.List;

public record AccommodationCreateRequest(
        @NotNull
        AccommodationType type,
        @NotNull
        RoomInformation roomInformation,
        @NotNull
        AccommodationLocationRequest address,
        @NotNull
        String name,
        @NotNull
        String description,
        @NotNull
        List<String> pictures,

        @PositiveOrZero
        int basePricePerDay
) {
    public AccommodationEntity toEntity(UserEntity host) {
        AccommodationEntity newAccommodation = AccommodationEntity.builder()
                .host(host)
                .type(type)
                .roomInformation(roomInformation)
                .address(address.toLocation())
                .name(name)
                .description(description)
                .pictures(new ArrayList<>())
                .products(new ArrayList<>())
                .basePricePerDay(basePricePerDay)
                .build();

        pictures.forEach(newAccommodation::addPicture);

        return newAccommodation;
    }
}
