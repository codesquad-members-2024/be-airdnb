package team10.airdnb.reservation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.exception.AccommodationIdNotFoundException;
import team10.airdnb.accommodation.repository.AccommodationRepository;
import team10.airdnb.jwt.service.TokenManager;
import team10.airdnb.member.entity.Member;
import team10.airdnb.member.exception.MemberIdNotFoundException;
import team10.airdnb.member.repository.MemberRepository;
import team10.airdnb.reservation.controller.request.ReservationCreateRequest;
import team10.airdnb.reservation.controller.response.ReservationSummaryResponse;
import team10.airdnb.reservation.controller.response.ReservationInformationResponse;
import team10.airdnb.reservation.entity.Reservation;
import team10.airdnb.reservation.exception.ReservationIdNotFoundException;
import team10.airdnb.reservation.exception.ReservationUnavailableException;
import team10.airdnb.reservation.repository.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final AccommodationRepository accommodationRepository;

    private final TokenManager tokenManager;

    public List<ReservationSummaryResponse> getReservations() {
        return reservationRepository.findAll().stream()
                .map(ReservationSummaryResponse::from)
                .collect(Collectors.toList());
    }

    public ReservationInformationResponse getReservation(long reservationId) {
        Reservation reservation = getReservationById(reservationId);

        return ReservationInformationResponse.from(reservation);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ReservationSummaryResponse createReservation(ReservationCreateRequest request, String authorizationHeader) {
        validateReservationAvailable(request); // 중복 체크

        String token = getToken(authorizationHeader);

        tokenManager.validateToken(token);

        String memberId = (String) tokenManager.getTokenClaims(token).get(("memberId"));

        Member member = getMemberById(memberId);

        Accommodation accommodation = getAccommodationById(request.accommodationId());

        Reservation reservation = request.toEntity(member, accommodation);

        reservationRepository.save(reservation);

        return ReservationSummaryResponse.from(reservation);
    }

    @Transactional
    public ReservationSummaryResponse deleteReservation(long reservationId) {
        Reservation reservation = getReservationById(reservationId);

        reservationRepository.delete(reservation);

        return ReservationSummaryResponse.from(reservation);
    }

    private String getToken(String authorizationHeader){
        return authorizationHeader.split(" ")[1];
    }

    private void validateReservationAvailable(ReservationCreateRequest request) {
        boolean isAvailable = reservationRepository.isDateRangeAvailable(
                request.accommodationId(),
                request.checkInDate(),
                request.checkOutDate()
        );

        if (!isAvailable) {
            throw new ReservationUnavailableException();
        }
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


}
