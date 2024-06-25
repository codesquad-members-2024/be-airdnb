package com.team01.airdnb.admin;

import com.team01.airdnb.accommadation.AccommodationService;
import com.team01.airdnb.accommadation.dto.AccommodationShowResponse;
import com.team01.airdnb.reservation.ReservationService;
import com.team01.airdnb.reservation.dto.ReservationShowResponse;
import com.team01.airdnb.user.UserService;
import com.team01.airdnb.user.dto.UserShowResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
  private final ReservationService reservationService;
  private final UserService userService;
  private final AccommodationService accommodationService;

  public AdminService(ReservationService reservationService, UserService userService,
      AccommodationService accommodationService) {
    this.reservationService = reservationService;
    this.userService = userService;
    this.accommodationService = accommodationService;
  }

  public List<ReservationShowResponse> getAllReservations() {
    return reservationService.findAllForAdmin();
  }

  public List<UserShowResponse> getAllUsers() {
    return userService.findAllForAdmin();
  }

  public List<AccommodationShowResponse> getAllAccommodations() {
    return accommodationService.findAllForAdmin();
  }

  public void deleteUser(long userId) {
    userService.delete(userId);
  }

  public void deleteReservation(long reservationId) {
    reservationService.delete(reservationId);
  }

  public void deleteAccommodation(long accommodationId){
    accommodationService.delete(accommodationId);
  }
}
