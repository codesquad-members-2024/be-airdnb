package com.team01.airdnb.reservation;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.accommadation.AccommodationService;
import com.team01.airdnb.reservation.dto.ReservationRequest;
import com.team01.airdnb.reservation.dto.ReservationShowResponse;
import com.team01.airdnb.user.User;
import com.team01.airdnb.user.UserService;
import java.util.NoSuchElementException;
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

  public void createReservation(ReservationRequest reservationRequest) {
    User user = userService.FindUserById(reservationRequest.userId());
    Accommodation accommodation = accommodationService.findById(
        reservationRequest.accommodationId());

    if (checkAvailability(reservationRequest)) {
      throw new IllegalArgumentException("예약이 불가능합니다.");
    }

    Reservation reservation = reservationRequest.toEntity(user, accommodation);
    reservationRepository.save(reservation);
  }

  private boolean checkAvailability(ReservationRequest reservationRequest) {
    Integer count = reservationRepository.countReservationById(
        reservationRequest.accommodationId(),
        reservationRequest.startDate(), reservationRequest.endDate(),
        reservationRequest.adults(), reservationRequest.children(),
        reservationRequest.infants(), reservationRequest.pets());
    return count != 0;
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

  @Transactional(readOnly = true)
  public ReservationShowResponse showReservation(Long reservationId) {
    Reservation reservation = reservationRepository.findById(reservationId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 예약입니다."));

    return ReservationShowResponse.builder()
        .reservationID(reservation.getId())
        .accommodationID(reservation.getAccommodation().getId())
        .startDate(reservation.getStartDate())
        .endDate(reservation.getEndDate())
        .adults(reservation.getAdults())
        .children(reservation.getChildren())
        .infants(reservation.getInfants())
        .pets(reservation.getPets())
        .price(reservation.getPrice())
        .userId(reservation.getUser().getId())
        .build();
  }
}
