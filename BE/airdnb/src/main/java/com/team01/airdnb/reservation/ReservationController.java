package com.team01.airdnb.reservation;

import com.team01.airdnb.reservation.dto.ReservationHostListResponse;
import com.team01.airdnb.reservation.dto.ReservationRequest;
import com.team01.airdnb.reservation.dto.ReservationShowResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
  private final ReservationService reservationService;

  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping
  public void createReservation(@RequestBody ReservationRequest reservationRequest){
    reservationService.createReservation(reservationRequest);
  }

  @PutMapping("/{reservationId}")
  public void updateReservation(@PathVariable Long reservationId,
      @RequestBody ReservationRequest reservationRequest){
    reservationService.updateReservation(reservationId, reservationRequest);
  }

  @DeleteMapping("/{reservationId}")
  public void deleteReservation(@PathVariable Long reservationId){
    //todo: 회원아이디와 레저베이션의 유저아이디가 같은지 확인 -> 이거 인터셉터에서 접근 막아줄 수 없을까요?
    reservationService.deleteReservation(reservationId);
  }

  @GetMapping("/{reservationId}")
  public ReservationShowResponse showReservation(@PathVariable Long reservationId) {
    return reservationService.showReservation(reservationId);
  }
}

