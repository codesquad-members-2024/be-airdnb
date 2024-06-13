package com.airdnb.clone.domain.reservation.response;

import com.airdnb.clone.domain.reservation.entity.Reservation;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class ReservationResponse {
    private final Long reservationId;
    private final Long memberId;
    private final Long stayId;
    private final LocalDateTime checkIn;
    private final LocalDateTime checkOut;
    private final Long totalRate;
    private final Integer guestCount;

    public static ReservationResponse of(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getMember().getId(),
                reservation.getStay().getId(),
                reservation.getCheckIn(),
                reservation.getCheckOut(),
                reservation.getTotalRate(),
                reservation.getGuest().getGuestCount()
        );
    }
}
