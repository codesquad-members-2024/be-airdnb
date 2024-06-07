package com.airdnb.reservation;

import com.airdnb.member.MemberService;
import com.airdnb.member.entity.Member;
import com.airdnb.reservation.dto.ReservationCreateRequest;
import com.airdnb.reservation.entity.Reservation;
import com.airdnb.reservation.entity.ReservationPeriod;
import com.airdnb.stay.StayService;
import com.airdnb.stay.entity.Stay;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final StayService stayService;
    private final MemberService memberService;

    public Long createReservation(ReservationCreateRequest reservationCreateRequest) {
        Stay stay = stayService.findStayById(reservationCreateRequest.getStayId());
        ReservationPeriod reservationPeriod = getReservationPeriod(stay, reservationCreateRequest.getCheckinAt(),
                reservationCreateRequest.getCheckoutAt());
        validateGuestsCount(stay.getMaxGuests(), reservationCreateRequest.getGuestCount());
        Member customer = memberService.findMemberById(reservationCreateRequest.getCustomerId());
        Double paymentAmount = getPaymentAmount(stay.getPrice(),
                Objects.requireNonNull(reservationPeriod).getDaysOfStay());

        Reservation reservation = buildReservation(reservationCreateRequest, stay, customer,
                reservationPeriod, paymentAmount);
        reservationRepository.save(reservation);

        return reservation.getId();
    }

    private void validateGuestsCount(Integer maxGuests, Integer guestCount) {
        if (guestCount > maxGuests) {
            throw new InvalidReservationException("예약 신청 인원이 수용 가능 인원을 초과하였습니다.");
        }
    }

    private static Reservation buildReservation(ReservationCreateRequest reservationCreateRequest, Stay stay,
                                                Member customer, ReservationPeriod reservationPeriod,
                                                Double paymentAmount) {
        return Reservation.builder()
                .stay(stay)
                .customer(customer)
                .reservationPeriod(reservationPeriod)
                .paymentAmount(paymentAmount)
                .guestCount(reservationCreateRequest.getGuestCount())
                .build();
    }

    private ReservationPeriod getReservationPeriod(Stay stay, LocalDateTime checkinAt, LocalDateTime checkoutAt) {
        LocalDateTime startDate = stay.getStartDate();
        LocalDateTime endDate = stay.getEndDate();
        if (checkinAt.isBefore(startDate) || checkoutAt.isAfter(endDate)) {
            throw new InvalidReservationException("예약 가능한 날짜가 아닙니다.");
        }
        return new ReservationPeriod(checkinAt, checkoutAt);
    }

    private Double getPaymentAmount(int price, long reservationDay) {
        // 할인 정책 로직 생기면 적용 가능;
        return (double) (price * reservationDay);
    }
}
