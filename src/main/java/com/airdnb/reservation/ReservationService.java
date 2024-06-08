package com.airdnb.reservation;

import com.airdnb.global.NotFoundException;
import com.airdnb.member.MemberService;
import com.airdnb.member.entity.Member;
import com.airdnb.reservation.dto.ReservationCreateRequest;
import com.airdnb.reservation.dto.ReservationQueryResponse;
import com.airdnb.reservation.entity.Reservation;
import com.airdnb.reservation.entity.ReservationPeriod;
import com.airdnb.stay.StayService;
import com.airdnb.stay.entity.Stay;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
        ReservationPeriod reservationPeriod = confirmReservationPeriod(stay, reservationCreateRequest.getCheckinAt(),
                reservationCreateRequest.getCheckoutAt());
        validateGuestsCount(stay.getMaxGuests(), reservationCreateRequest.getGuestCount());
        Member customer = getCustomer(stay);
        Double paymentAmount = calculatePaymentAmount(stay.getPrice(),
                Objects.requireNonNull(reservationPeriod).getDaysOfStay());

        Reservation reservation = buildReservation(reservationCreateRequest, stay, customer,
                reservationPeriod, paymentAmount);
        reservationRepository.save(reservation);

        return reservation.getId();
    }

    @Transactional(readOnly = true)
    public ReservationQueryResponse queryReservationDetail(Long id) {
        Reservation reservation = findReservationById(id);
        return ReservationQueryResponse.builder()
                .id(reservation.getId())
                .customerName(reservation.getCustomer().getName())
                .createdAt(reservation.getCreatedAt())
                .status(reservation.getStatus())
                .hostName(reservation.getStay().getHost().getName())
                .stayName(reservation.getStay().getName())
                .checkinAt(reservation.getReservationPeriod().getCheckinAt())
                .checkoutAt(reservation.getReservationPeriod().getCheckoutAt())
                .paymentAmount(reservation.getPaymentAmount())
                .guestCount(reservation.getGuestCount())
                .build();
    }

    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("id와 일치하는 예약을 찾을 수 없습니다."));
    }

    private void validateGuestsCount(Integer maxGuests, Integer guestCount) {
        if (guestCount > maxGuests) {
            throw new InvalidReservationException("예약 신청 인원이 수용 가능 인원을 초과하였습니다.");
        }
    }

    private Reservation buildReservation(ReservationCreateRequest reservationCreateRequest, Stay stay,
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

    private ReservationPeriod confirmReservationPeriod(Stay stay, LocalDateTime checkinAt, LocalDateTime checkoutAt) {
        ReservationPeriod reservationPeriod = new ReservationPeriod(checkinAt, checkoutAt);
        if (!stay.isSatisfyingPeriod(reservationPeriod)) {
            throw new InvalidReservationException("해당 날짜는 예약이 불가능합니다.");
        }
        List<LocalDate> reservationDates = reservationPeriod.getReservationDates();
        stay.addClosedDates(reservationDates);
        return reservationPeriod;
    }

    private Double calculatePaymentAmount(int price, long reservationDay) {
        // 할인 정책 로직 생기면 적용 가능;
        return (double) (price * reservationDay);
    }

    private Member getCustomer(Stay stay) {
        String currentMemberId = memberService.getCurrentMemberId();
        if (stay.hasSameHostId(currentMemberId)) {
            throw new InvalidReservationException("예약자와 호스트는 동일할 수 없습니다.");
        }
        return memberService.findMemberById(currentMemberId);
    }
}
