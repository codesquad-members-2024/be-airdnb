package com.airdnb.reservation;

import com.airdnb.global.exception.NotFoundException;
import com.airdnb.member.MemberService;
import com.airdnb.member.entity.Member;
import com.airdnb.reservation.dto.ReservationCreate;
import com.airdnb.reservation.dto.ReservationQuery;
import com.airdnb.reservation.entity.Reservation;
import com.airdnb.reservation.entity.ReservationPeriod;
import com.airdnb.reservation.entity.ReservationStatus;
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

    public Long createReservation(ReservationCreate reservationCreate) {
        Stay stay = stayService.findActiveStayById(reservationCreate.getStayId());
        ReservationPeriod reservationPeriod = confirmReservationPeriod(stay, reservationCreate.getCheckinAt(),
                reservationCreate.getCheckoutAt());
        ReservationValidator.validateGuestsCount(stay, reservationCreate.getGuestCount());
        Member customer = getCustomer(stay);
        Double paymentAmount = calculatePaymentAmount(stay.getPrice(),
                Objects.requireNonNull(reservationPeriod).getDaysOfStay());

        Reservation reservation = buildReservation(reservationCreate, stay, customer,
                reservationPeriod, paymentAmount);
        reservationRepository.save(reservation);

        return reservation.getId();
    }

    @Transactional(readOnly = true)
    public ReservationQuery queryReservationDetail(Long id) {
        Reservation reservation = findReservationById(id);
        String currentMemberId = memberService.getCurrentMemberId();
        reservation.validateQueryAuthority(currentMemberId);

        return ReservationQuery.builder()
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

    @Transactional(readOnly = true)
    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new NotFoundException("id와 일치하는 예약을 찾을 수 없습니다."));
    }

    public void updateReservationStatus(Long id, String status) {
        Reservation reservation = findReservationById(id);
        ReservationStatus requestStatus = ReservationStatus.of(status);
        String currentMemberId = memberService.getCurrentMemberId();

        ReservationValidator.validateReservationStatus(requestStatus);

        if (requestStatus == ReservationStatus.APPROVED || requestStatus == ReservationStatus.REJECTED) {
            reservation.handleReservation(currentMemberId, requestStatus);
            return;
        }
        reservation.cancelReservation(currentMemberId);
    }

    private Reservation buildReservation(ReservationCreate reservationCreate, Stay stay,
                                         Member customer, ReservationPeriod reservationPeriod,
                                         Double paymentAmount) {
        return Reservation.builder()
                .stay(stay)
                .customer(customer)
                .reservationPeriod(reservationPeriod)
                .paymentAmount(paymentAmount)
                .guestCount(reservationCreate.getGuestCount())
                .build();
    }

    private ReservationPeriod confirmReservationPeriod(Stay stay, LocalDateTime checkinAt, LocalDateTime checkoutAt) {
        ReservationPeriod reservationPeriod = new ReservationPeriod(checkinAt, checkoutAt);

        ReservationValidator.validateReservationPeriod(stay, reservationPeriod);

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

        ReservationValidator.validateCustomer(stay, currentMemberId);

        return memberService.findMemberById(currentMemberId);
    }
}
