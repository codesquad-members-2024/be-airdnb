package com.team01.airdnb.admin;

import com.team01.airdnb.accommadation.dto.AccommodationShowResponse;
import com.team01.airdnb.reservation.dto.ReservationShowResponse;
import com.team01.airdnb.user.dto.UserShowResponse;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
  private final AdminService adminService;

  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }
  @GetMapping("/users")
  public List<UserShowResponse> getAllUsers() {
    return adminService.getAllUsers();
  }

  @GetMapping("/reservations")
  public List<ReservationShowResponse> getReservations() {
    return adminService.getAllReservations();
  }

  @GetMapping("/accommodations")
  public List<AccommodationShowResponse> getAccommodation(){
    return adminService.getAllAccommodations();
  }

  @DeleteMapping("/users/{userId}")
  public void deleteUser(@PathVariable("userId") Long userId){
    adminService.deleteUser(userId);
  }

  @DeleteMapping("/reservations/{reservationId}")
  public void deleteReservation(@PathVariable("reservationId") Long reservationId){
    adminService.deleteReservation(reservationId);
  }

  @DeleteMapping("/accommodations/{accommodationId}")
  public void deleteAccommodation(@PathVariable("accommodationId") Long accommodationId){
    adminService.deleteAccommodation(accommodationId);
  }
}
