package codesquad.team05.service.reservation;

import codesquad.team05.domain.reservation.Reservation;
import codesquad.team05.domain.reservation.ReservationRepository;
import codesquad.team05.web.reservation.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationResponse findOneById(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        return reservation.toEntity();
    }
}
