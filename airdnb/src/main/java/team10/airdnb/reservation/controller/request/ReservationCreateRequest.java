package team10.airdnb.reservation.controller.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import team10.airdnb.accommodation.entity.embedded.AccommodationFee;
import team10.airdnb.reservation.entity.Reservation;
import team10.airdnb.member.entity.Member;
import team10.airdnb.accommodation.entity.Accommodation;

public record ReservationCreateRequest(
        Long accommodationId,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer capacity
) {
    public Reservation toEntity(Member member, Accommodation accommodation) {
        return Reservation.builder()
                .member(member)
                .accommodation(accommodation)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .isConfirmed(false)
                .capacity(capacity)
                .totalPrice(calculateFee(accommodation.getAccommodationFee()))
                .build();
    }

    private BigDecimal calculateFee(AccommodationFee fee) {
        BigDecimal numberOfDays = BigDecimal.valueOf(ChronoUnit.DAYS.between(checkInDate, checkOutDate));
        BigDecimal dayRate = fee.getDayRate();
        BigDecimal cleaningFee = fee.getCleaningFee();

        BigDecimal totalDayRate = dayRate.multiply(numberOfDays);
        BigDecimal totalPrice = totalDayRate.add(cleaningFee);

        BigDecimal serviceFee = totalPrice.multiply(BigDecimal.valueOf(0.03));

        totalPrice = totalPrice.add(serviceFee);

        return totalPrice;
    }
}
