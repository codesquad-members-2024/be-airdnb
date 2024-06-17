package team10.airdnb.accommodation.controller.request;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.entity.embedded.AccommodationFee;
import team10.airdnb.accommodation.entity.embedded.Address;
import team10.airdnb.accommodation.entity.embedded.Coordinate;
import team10.airdnb.accommodation.entity.embedded.RoomInfo;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;
import team10.airdnb.accommodation_type.entity.AccommodationType;

import java.util.List;

public record AccommodationCreateRequest(
        @NotBlank
        @Email
        String memberId,

        @NotBlank
        String name,

        @Min(1) @Max(16)
        Long maxCapacity,

        Long accommodationType,

        Long accommodationRoomType,

        String accommodationDescription,

        String accommodationImages,

        @Min(0) @Max(50)
        int bedroomCount,

        @Min(1) @Max(50)
        int bathroomCount,

        @Min(1) @Max(50)
        int bedCount,

        @Min(10000) @Max(15000000)
        int dayRate,

        @Min(0) @Max(1000000)
        int cleaningFee,

        String city,

        String district,

        String neighborhood,

        String streetName,

        String detailedAddress,

        Double latitude,

        Double longitude,

        @Nullable
        List<Long> amenityIds
) {
    public Accommodation toEntity(AccommodationType accommodationType, AccommodationRoomType accommodationRoomType) {
        return Accommodation.builder()
                .memberId(memberId)
                .name(name)
                .maxCapacity(maxCapacity)
                .accommodationType(accommodationType)
                .accommodationRoomType(accommodationRoomType)
                .accommodationDescription(accommodationDescription)
                .accommodationImages(accommodationImages)
                .roomInfo(RoomInfo.builder()
                        .bedroomCount(bedroomCount)
                        .bathroomCount(bathroomCount)
                        .bedCount(bedCount)
                        .build())
                .accommodationFee(AccommodationFee.builder()
                        .dayRate(dayRate)
                        .cleaningFee(cleaningFee)
                        .build())
                .address(Address.builder()
                        .city(city)
                        .district(district)
                        .neighborhood(neighborhood)
                        .streetName(streetName)
                        .detailedAddress(detailedAddress)
                        .build())
                .coordinate(Coordinate.builder()
                        .latitude(latitude)
                        .longitude(longitude)
                        .build())
                .build();
    }

}
