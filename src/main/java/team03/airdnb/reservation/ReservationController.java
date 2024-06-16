package team03.airdnb.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import team03.airdnb.reservation.dto.request.ReservationSaveDto;

import java.net.URI;

@RequestMapping("/reservations")
@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Void> createReservation(@RequestBody ReservationSaveDto reservationSaveDto, UriComponentsBuilder uriComponentsBuilder) {
        Long reservationId = reservationService.createReservation(reservationSaveDto);
        URI location = uriComponentsBuilder.path("/reservations/{id}")
                .buildAndExpand(reservationId)
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
