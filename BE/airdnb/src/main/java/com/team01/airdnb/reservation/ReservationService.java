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

  private final ReservationRepository reservationRepository;
  private final UserService userService;
  private final AccommodationService accommodationService;

  @Autowired
  public ReservationService(ReservationRepository reservationRepository, UserService userService,
      AccommodationService accommodationService) {
    this.reservationRepository = reservationRepository;
    this.userService = userService;
    this.accommodationService = accommodationService;
  }

  public void createReservation(ReservationCreateRequest reservationCreateRequest) {
    User user = userService.FindUserById(reservationCreateRequest.getUserId());
    Accommodation accommodation = accommodationService.findById(
        reservationCreateRequest.getAccommodationId());

    if (!checkAvailability(reservationCreateRequest)) {
      throw new IllegalArgumentException("예약이 불가능합니다.");
    }

    Reservation reservation = reservationCreateRequest.toEntity(user, accommodation);
    reservationRepository.save(reservation);
  }

  private boolean checkAvailability(ReservationCreateRequest reservationCreateRequest) {
    Integer count = reservationRepository.countReservationById(
        reservationCreateRequest.getAccommodationId(),
        reservationCreateRequest.getStartDate(), reservationCreateRequest.getEndDate(),
        reservationCreateRequest.getAdults(), reservationCreateRequest.getChildren(),
        reservationCreateRequest.getInfants(), reservationCreateRequest.getPets());
    return count == 0;
  }

  public void deleteReservation(Long reservationId) {
    reservationRepository.deleteById(reservationId);
  }

  public void updateReservation(Long reservationId,
      ReservationRequest reservationRequest) {
    Reservation reservation = reservationRepository.findById(reservationId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 예약입니다."));

    if (checkAvailability(reservationRequest)) {
      throw new IllegalArgumentException("예약이 불가능합니다.");
    }

    reservation.update(reservationRequest);
  }
}
