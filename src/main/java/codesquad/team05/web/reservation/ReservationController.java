package codesquad.team05.web.reservation;

import codesquad.team05.domain.reservation.Reservation;
import codesquad.team05.domain.reservation.ReservationRepository;
import codesquad.team05.domain.user.User;
import codesquad.team05.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    // Dto 사용 및 로직 수정 예정
    @PostMapping("/reservations")
    public void reserve(@RequestBody String loginId) {
        User user = userRepository.findById(loginId).get();
        Reservation reservation = Reservation.builder()
                .amount(10000)
                .personCount(3)
                .user(user)
                .accommodation(null)
                .build();
        reservationRepository.save(reservation);
    }

    @GetMapping("/reservations/{reservationId}")
    public Reservation reservationDetails(@PathVariable Long reservationId) {
        return reservationRepository.findById(reservationId).get(); // Optional 처리 예정
    }
}
