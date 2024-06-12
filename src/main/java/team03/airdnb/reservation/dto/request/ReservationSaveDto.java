package team03.airdnb.reservation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.reservation.Reservation;
import team03.airdnb.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReservationSaveDto {

    private LocalDate checkIn;
    private LocalDate checkOut;
    private int headCount;
    private Long userId;
    private Long accommodationId;
    private LocalDateTime createdAt;

    public Reservation toEntity(User user, Accommodation accommodation) {
        return Reservation.builder()
                .checkIn(this.checkIn)
                .checkOut(this.checkOut)
                .headCount(this.headCount)
                .user(user)
                .accommodation(accommodation)
                .build();
    }
}
