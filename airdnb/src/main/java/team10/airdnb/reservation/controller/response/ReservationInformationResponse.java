package team10.airdnb.reservation.controller.response;

import team10.airdnb.reservation.dto.AccommodationInformationDto;
import team10.airdnb.reservation.entity.Reservation;

import java.time.LocalDate;

public record ReservationInformationResponse(
        long reservationId,
        String memberId,
        AccommodationInformationDto accommodationInformation,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        long capacity,
        boolean isConfirmed,
        long totalPrice
) {
    public static ReservationInformationResponse from(Reservation reservation, AccommodationInformationDto accommodationInformation) {
        return new ReservationInformationResponse(
                reservation.getId(),
                reservation.getMember().getId(),
                accommodationInformation,
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getCapacity(),
                reservation.isConfirmed(),
                reservation.getTotalPrice()
        );
    }
}
