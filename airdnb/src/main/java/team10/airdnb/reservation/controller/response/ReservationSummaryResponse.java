package team10.airdnb.reservation.controller.response;


import team10.airdnb.accommodation.dto.AccommodationInformationDto;
import team10.airdnb.member.dto.MemberInformationDto;
import team10.airdnb.reservation.entity.Reservation;

public record ReservationSummaryResponse(
        long reservationId,
        MemberInformationDto memberInformation,
        AccommodationInformationDto accommodationInformation
) {
    public static ReservationSummaryResponse from(Reservation reservation) {
        return new ReservationSummaryResponse(
                reservation.getId(),
                MemberInformationDto.from(reservation.getMember()),
                AccommodationInformationDto.from(reservation.getAccommodation())
        );
    }

}
