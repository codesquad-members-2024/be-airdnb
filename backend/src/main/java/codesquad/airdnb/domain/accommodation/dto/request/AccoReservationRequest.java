package codesquad.airdnb.domain.accommodation.dto.request;

import codesquad.airdnb.domain.accommodation.entity.Reservation;
import codesquad.airdnb.domain.accommodation.entity.ReservationStatus;
import codesquad.airdnb.domain.member.Member;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record AccoReservationRequest (

        @NotNull
        @Min(value = 1)
        Long accoId,

        @NotNull
        @Min(value = 1, message = "최소 한명의 성인이 있어야 합니다.")
        Long adultCount,

        @Min(value = 0)
        Long childCount,

        @Min(value = 0)
        Long infantCount,

        @NotNull
        @FutureOrPresent
        LocalDate startDate,

        @NotNull
        @Future
        LocalDate endDate,

        @NotNull
        @Size(min = 2)
        List<Long> products
) {
        public AccoReservationRequest {
                if (endDate.isBefore(startDate.plusDays(1))) {
                        throw new IllegalArgumentException("종료일은 시작일보다 최소 하루 늦어야 합니다.");
                }
                // 시작날부터 끝날까지 몇일인지 확인하고, 예약하려는 products가 충분한 크기인지 확인한다.
                int diff = endDate.compareTo(startDate);
                if (diff != products.size()) {
                        throw new IllegalArgumentException("예약하려는 날짜와 상품의 개수가 다릅니다.");
                }
        }

        public Reservation toReservation(Member member, Long finalPrice) {
                return Reservation.builder()
                        .member(member)
                        .adultCount(adultCount)
                        .childCount(childCount)
                        .infantCount(infantCount)
                        .checkInDate(startDate)
                        .checkOutDate(endDate)
                        .finalPrice(finalPrice)
                        .status(ReservationStatus.PENDING)
                        .build();
        }
}
