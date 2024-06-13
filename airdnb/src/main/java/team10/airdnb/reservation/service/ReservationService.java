package team10.airdnb.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.exception.AccommodationIdNotFoundException;
import team10.airdnb.accommodation.repository.AccommodationRepository;
import team10.airdnb.member.entity.Member;
import team10.airdnb.member.exception.MemberIdNotFoundException;
import team10.airdnb.member.repository.MemberRepository;
import team10.airdnb.reservation.controller.request.ReservationCreateRequest;
import team10.airdnb.reservation.controller.response.ReservationSummaryResponse;
import team10.airdnb.reservation.controller.response.ReservationInformationResponse;
import team10.airdnb.reservation.entity.Reservation;
import team10.airdnb.reservation.exception.ReservationIdNotFoundException;
import team10.airdnb.reservation.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final AccommodationRepository accommodationRepository;

    public List<ReservationSummaryResponse> getReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationSummaryResponse::from)
                .collect(Collectors.toList());
    }

    public ReservationInformationResponse getReservation(long reservationId) {
        Reservation reservation = getReservationById(reservationId);

        return ReservationInformationResponse.from(reservation);
    }

//    public ReservationSummaryResponse createReservation(ReservationCreateRequest request) {
//        Member member = getMemberById(request.memberId());
//
//        Accommodation accommodation = getAccommodationById(request.accommodationId());
//
//        Reservation reservation = request.toEntity(member, accommodation);
//
//        reservationRepository.save(reservation);
//
//        return ReservationSummaryResponse.from(reservation);
//    }

    public ReservationSummaryResponse createReservation(ReservationCreateRequest request) {
        Member member = getMemberById(request.memberId());

        Accommodation accommodation = getAccommodationById(request.accommodationId());

        // 총 비용 계산
        long totalPrice = calculateTotalPrice(accommodation, request.checkInDate(), request.checkOutDate());

        Reservation reservation = buildReservation(request, member, accommodation, totalPrice);

        reservationRepository.save(reservation);
        return ReservationSummaryResponse.from(reservation);
    }

    public ReservationSummaryResponse deleteReservation(long reservationId) {
        Reservation reservation = getReservationById(reservationId);

        reservationRepository.delete(reservation);

        return ReservationSummaryResponse.from(reservation);
    }

    private Reservation getReservationById(long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(ReservationIdNotFoundException::new);
    }

    private Member getMemberById(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberIdNotFoundException::new);
    }

    private Accommodation getAccommodationById(long accommodationId) {
        return accommodationRepository.findById(accommodationId)
                .orElseThrow(AccommodationIdNotFoundException::new);
    }

    // 총 비용을 계산
    private long calculateTotalPrice(Accommodation accommodation, LocalDate checkInDate, LocalDate checkOutDate) {
        //두 날짜 사이의 일 수를 계산
        long numberOfDays = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        int dayRate = accommodation.getAccommodationFee().getDayRate();
        int cleaningFee = accommodation.getAccommodationFee().getCleaningFee();
        return dayRate * numberOfDays + cleaningFee;
    }

    // 예약 엔티티를 생성
    private Reservation buildReservation(ReservationCreateRequest request, Member member, Accommodation accommodation, long totalPrice) {
        return Reservation.builder()
                .member(member)
                .accommodation(accommodation)
                .checkInDate(request.checkInDate())
                .checkOutDate(request.checkOutDate())
                .capacity(request.capacity())
                .isConfirmed(false)
                .totalPrice(totalPrice)
                .build();
    }
}
