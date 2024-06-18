package codesquad.team05.web.reservation;

import codesquad.team05.service.reservation.ReservationBusinessService;
import codesquad.team05.service.reservation.ReservationService;
import codesquad.team05.web.reservation.dto.request.ReservationSave;
import codesquad.team05.web.reservation.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    // to-do
    // User 기능 완성하면 @AuthenticationPrincipal PrincipalDetails userDetails을 활용해서 세션으로 userID집어넣기
    private final ReservationService reservationService;
    private final ReservationBusinessService reservationBusinessService;

    @PostMapping("/{id}")
    public void reserve(@PathVariable Long id, @RequestBody ReservationSave requestForm) {
        reservationBusinessService.reservation(1L, id, requestForm.toServiceDto());

    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> get(@PathVariable Long id) {
        ReservationResponse reservationResponse = reservationService.get(id);
        return ResponseEntity.ok(reservationResponse);
    }
}
