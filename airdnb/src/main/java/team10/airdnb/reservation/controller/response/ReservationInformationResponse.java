package team10.airdnb.reservation.controller.response;


import team10.airdnb.accommodation.dto.AccommodationInformationDto;
import team10.airdnb.member.dto.MemberInformationDto;
import team10.airdnb.reservation.entity.Reservation;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationInformationResponse(
        Long reservationId,
        MemberInformationDto memberInformation,
        AccommodationInformationDto accommodationInformation,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer capacity,
        boolean isConfirmed,
        BigDecimal totalPrice
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
