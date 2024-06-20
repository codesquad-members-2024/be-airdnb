package com.team01.airdnb.reservation;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.accommadation.AccommodationService;
import com.team01.airdnb.reservation.dto.ReservationCreateRequest;
import com.team01.airdnb.reservation.dto.ReservationUpdateRequest;
import com.team01.airdnb.user.User;
import com.team01.airdnb.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReservationService {

  private ReservationRepository reservationRepository;
  private UserService userService;
  private AccommodationService accommodationService;

  @Autowired
  public ReservationService(ReservationRepository reservationRepository, UserService userService,
      AccommodationService accommodationService) {
    this.reservationRepository = reservationRepository;
    this.userService = userService;
    this.accommodationService = accommodationService;
  }

  public void createReservation(ReservationCreateRequest reservationCreateRequest) {
    User user = userService.FindUserById(reservationCreateRequest.getUserId());
    Accommodation accommodation = accommodationService.findAccommodation(
        reservationCreateRequest.getAccommodationId());

    if (checkAvailability(reservationCreateRequest)){
      Reservation reservation = reservationCreateRequest.toEntity(user, accommodation);
      reservationRepository.save(reservation);
    }
  }

  private boolean checkAvailability(ReservationCreateRequest reservationCreateRequest) {
    Integer count = reservationRepository.countReservationById(reservationCreateRequest.getAccommodationId(),
        reservationCreateRequest.getStartDate(),reservationCreateRequest.getEndDate());
    if (count == 0) return true;
    return false;
  }

  public void deleteReservation(Long reservationId) {
    reservationRepository.deleteById(reservationId);
  }

  public void updateReservation(Long reservationId,
      ReservationUpdateRequest reservationUpdateRequest) {
  }
}
