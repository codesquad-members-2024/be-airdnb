package com.airdnb.clone.domain.reservation;

import com.airdnb.clone.domain.reservation.request.ReservationSaveRequest;
import com.airdnb.clone.domain.reservation.request.ReservationUpdateRequest;
import com.airdnb.clone.domain.reservation.response.ReservationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ReservationResponse create(@Valid @RequestBody ReservationSaveRequest request) {
        return reservationService.create(request.toBuilder(), request.getStayId(), request.getMemberId());
    }

    @GetMapping("/{id}")
    public ReservationResponse findReservation(@PathVariable("id") Long id) {
        return reservationService.findById(id);
    }

    @PatchMapping("/{id}")
    public void edit(@PathVariable("id") Long id, @Valid @RequestBody ReservationUpdateRequest request) {
        reservationService.edit(id, request.toGuest());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        reservationService.delete(id);
    }
}
