package codesquad.team05.web.reservation.dto.request;


import codesquad.team05.domain.reservation.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
public class ReservationServiceDto {

    private LocalDateTime registeredAt;
    private int personCount;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Long couponsToUse;

    public Reservation toEntityForSave(int amount) {
        Reservation reservation = new Reservation();
        reservation.setRegisteredAt(LocalDateTime.now());
        reservation.setAmount(amount);
        reservation.setPersonCount(personCount);
        reservation.setCheckIn(checkIn);
        reservation.setCheckOut(checkOut);

        return reservation;
    }
}
