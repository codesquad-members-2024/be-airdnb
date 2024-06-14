package team10.airdnb.accommodation.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.entity.embedded.AccommodationFee;
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
        long maxCapacity,

        Long accommodationType,

        Long accommodationRoomType,

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

        List<Long> amenityIds
) {
    public Accommodation toEntity(AccommodationType accommodationType, AccommodationRoomType accommodationRoomType) {
        return Accommodation.builder()
                .memberId(memberId)
                .name(name)
                .maxCapacity(maxCapacity)
                .accommodationType(accommodationType)
                .accommodationRoomType(accommodationRoomType)
                .roomInfo(RoomInfo.builder()
                        .bedroomCount(bedroomCount)
                        .bathroomCount(bathroomCount)
                        .bedCount(bedCount)
                        .build())
                .accommodationFee(AccommodationFee.builder()
                        .dayRate(dayRate)
                        .cleaningFee(cleaningFee)
                        .build())
                .build();
    }
}
