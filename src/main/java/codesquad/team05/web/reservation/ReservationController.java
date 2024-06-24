package codesquad.team05.web.reservation;

import codesquad.team05.service.ReservationService;
import codesquad.team05.service.reservation.ReservationBusinessService;
import codesquad.team05.web.reservation.dto.request.ReservationSave;
import codesquad.team05.web.reservation.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

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
    public ResponseEntity<Long> reserve(
            @PathVariable Long id,
            @RequestBody ReservationSave requestForm,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Long reservationId = reservationBusinessService.reserveAccommodation(1L, id, requestForm.toServiceDto());
        URI location = uriComponentsBuilder.path("/reservations/{id}")
                .buildAndExpand(reservationId)
                .toUri();
        return ResponseEntity
                .created(location)
                .body(reservationId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponse> reservationDetails(@PathVariable Long id) {
        return ResponseEntity
                .ok(reservationService.getReservation(id));
    }
}
