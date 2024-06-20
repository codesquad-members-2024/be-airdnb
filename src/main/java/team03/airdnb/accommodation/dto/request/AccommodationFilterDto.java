package team03.airdnb.accommodation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class AccommodationFilterDto {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double minPrice;
    private Double maxPrice;
    private Integer capacity;
    private Double currentLongitude; // 경도
    private Double currentLatitude; // 위도
}
