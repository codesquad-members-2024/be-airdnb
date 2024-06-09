package com.airdnb.reservation;

import com.airdnb.reservation.dto.ReservationCreateRequest;
import com.airdnb.reservation.dto.ReservationQueryResponse;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/{id}")
    public ResponseEntity<ReservationQueryResponse> queryReservationDetail(@PathVariable Long id) {
        ReservationQueryResponse reservationQueryResponse = reservationService.queryReservationDetail(id);
        return ResponseEntity.ok(reservationQueryResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateReservationStatus(@PathVariable Long id, @RequestParam String status) {
        reservationService.updateReservationStatus(id, status);
        return ResponseEntity.ok().build();
    }
}
