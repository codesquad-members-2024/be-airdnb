package team10.airdnb.reservation.controller.request;

import java.time.LocalDate;

import team10.airdnb.reservation.entity.Reservation;
import team10.airdnb.member.entity.Member;
import team10.airdnb.accommodation.entity.Accommodation;

public record ReservationCreateRequest(
        String memberId,
        long accommodationId,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        long capacity
) {
    public Reservation toEntity(Member member, Accommodation accommodation) {
        return Reservation.builder()
                .member(member)
                .accommodation(accommodation)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .capacity(capacity)
                .build();
    }
}
