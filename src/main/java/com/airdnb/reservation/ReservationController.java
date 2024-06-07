package com.airdnb.reservation;

import com.airdnb.reservation.dto.ReservationCreateRequest;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<Void> createReservation(
            @Valid @RequestBody ReservationCreateRequest reservationCreateRequest) {
        Long reservationId = reservationService.createReservation(reservationCreateRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reservationId)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
