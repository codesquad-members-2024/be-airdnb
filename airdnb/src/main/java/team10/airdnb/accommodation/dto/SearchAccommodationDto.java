package team10.airdnb.accommodation.dto;

import team10.airdnb.accommodation.entity.Accommodation;

import java.math.BigDecimal;

public record SearchAccommodationDto(
        long accommodationId,
        String accommodationName,
        String address,
        BigDecimal dayRate,
        Long maxCapacity
) {
    public static SearchAccommodationDto from(Accommodation accommodation) {
        return new SearchAccommodationDto(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getAddress().toString(), // 주소 필드를 문자열로 변환
                accommodation.getAccommodationFee().getDayRate(),
                accommodation.getMaxCapacity()
        );
    }
}
