package com.team01.airdnb.reservation;

import com.team01.airdnb.reservation.dto.ReservationCreateRequest;
import com.team01.airdnb.reservation.dto.ReservationUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {
  private ReservationService reservationService;

  @Autowired
  public ReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PostMapping
  public void createReservation(@RequestBody ReservationCreateRequest reservationCreateRequest){
    reservationService.createReservation(reservationCreateRequest);
  }

  @PutMapping("/{reservationId}")
  public void updateReservation(@PathVariable Long reservationId,
      @RequestBody ReservationRequest reservationRequest){
    reservationService.updateReservation(reservationId, reservationRequest);
  }

  @DeleteMapping("/{reservationId}")
  public void deleteReservation(@PathVariable Long reservationId){
    //회원아이디와 레저베이션의 유저아이디가 같은지 확인
    reservationService.deleteReservation(reservationId);
  }

}
