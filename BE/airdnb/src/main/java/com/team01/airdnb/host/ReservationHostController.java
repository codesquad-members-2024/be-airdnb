package com.team01.airdnb.host;

import com.team01.airdnb.reservation.ReservationService;
import com.team01.airdnb.reservation.dto.ReservationHostListResponse;
import com.team01.airdnb.reservation.dto.ReservationRequest;
import org.springframework.security.core.Authentication;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/host/reservations")
public class ReservationHostController {
  private final ReservationService reservationService;

  @Autowired
  public ReservationHostController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @PutMapping("/{reservationId}")
  public void updateReservation(@PathVariable("reservationId") Long reservationId,
      @RequestBody ReservationRequest reservationRequest){
    reservationService.updateReservation(reservationId, reservationRequest);
  }

  @DeleteMapping("/{reservationId}")
  public void deleteReservation(@PathVariable("reservationId") Long reservationId){
    //todo: 회원아이디와 레저베이션의 유저아이디가 같은지 확인 -> 이거 인터셉터에서 접근 막아줄 수 없을까요?
    reservationService.deleteReservation(reservationId);
  }

  @GetMapping
  public List<ReservationHostListResponse> showOwnedAccommodationReservation(Authentication authentication){
    return reservationService.getOwnedAccommodationReservation(authentication);
  }
}
