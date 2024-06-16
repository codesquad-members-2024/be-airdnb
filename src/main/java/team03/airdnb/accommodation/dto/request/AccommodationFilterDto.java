package team03.airdnb.accommodation.dto.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccommodationFilterDto {
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double minPrice;
    private Double maxPrice;
    private Integer capacity;

}
