package team10.airdnb.reservation.controller.response;

import team10.airdnb.reservation.dto.AccommodationInformationDto;
import team10.airdnb.reservation.dto.MemberInformationDto;
import team10.airdnb.reservation.entity.Reservation;

import java.time.LocalDate;

public record ReservationInformationResponse(
        long reservationId,
        MemberInformationDto memberInformation,
        AccommodationInformationDto accommodationInformation,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        long capacity,
        boolean isConfirmed,
        long totalPrice
) {
    public static ReservationInformationResponse from(Reservation reservation) {
        return new ReservationInformationResponse(
                reservation.getId(),
                MemberInformationDto.from(reservation.getMember()),
                AccommodationInformationDto.from(reservation.getAccommodation()),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getCapacity(),
                reservation.isConfirmed(),
                reservation.getTotalPrice()
        );
    }
}
