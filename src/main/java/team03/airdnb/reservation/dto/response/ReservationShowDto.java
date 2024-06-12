package team03.airdnb.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team03.airdnb.accommodation.dto.response.AccommodationListDto;
import team03.airdnb.reservation.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReservationShowDto {

    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int headCount;
    private AccommodationListDto accommodationListDto;
    private LocalDateTime createdAt;

    public static ReservationShowDto of(Reservation reservation) {
        return new ReservationShowDto(
                reservation.getId(),
                reservation.getCheckIn(),
                reservation.getCheckOut(),
                reservation.getHeadCount(),
                AccommodationListDto.of(reservation.getAccommodation(), reservation.getAccommodation().getAccommodationAmenities()),
                reservation.getCreatedAt()
        );
    }
}
