package team10.airdnb.accommodation.dto;

import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.entity.embedded.AccommodationFee;
import team10.airdnb.accommodation.entity.embedded.Address;
import team10.airdnb.accommodation.entity.embedded.Coordinate;
import team10.airdnb.accommodation.entity.embedded.RoomInfo;

import java.math.BigDecimal;

public record SearchAccommodationDto(
        long accommodationId,
        String accommodationName,
        String accommodationImage,
        AccommodationFee fee,
        RoomInfo roomInfo,
        Address address,
        Coordinate coordinate,
        Long maxCapacity
) {
    public static SearchAccommodationDto from(Accommodation accommodation) {
        return new SearchAccommodationDto(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getAccommodationImages(),
                accommodation.getAccommodationFee(),
                accommodation.getRoomInfo(),
                accommodation.getAddress(),
                accommodation.getCoordinate(),
                accommodation.getMaxCapacity()
        );
    }
}
