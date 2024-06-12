package team10.airdnb.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team10.airdnb.reservation.controller.request.ReservationCreateRequest;
import team10.airdnb.reservation.controller.response.ReservationInformationResponse;
import team10.airdnb.reservation.entity.Reservation;
import team10.airdnb.reservation.service.ReservationService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationRestController {

    private final ReservationService reservationService;

    @GetMapping("/api/reservation/{reservationId}")
    public ResponseEntity<?> getReservation(@PathVariable long reservationId) {
        ReservationInformationResponse response = reservationService.getReservation(reservationId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/reservation")
    public ResponseEntity<?> createReservation(@RequestBody @Valid ReservationCreateRequest request) {

        Reservation savedReservation = reservationService.createReservation(request);

        log.info("예약 생성 완료 : # {} : 숙소 이름 : {}, 예약자 이름 : {}",
                savedReservation.getId(),
                savedReservation.getAccommodation().getName(),
                savedReservation.getMember().getMemberName());

        return ResponseEntity.ok(savedReservation);
    }

}
