package com.team01.airdnb.reservation;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.accommadation.AccommodationService;
import com.team01.airdnb.accommadation.dto.AccommodationHostResponse;
import com.team01.airdnb.reservation.dto.ReservationHostListResponse;
import com.team01.airdnb.reservation.dto.ReservationRequest;
import com.team01.airdnb.reservation.dto.ReservationShowResponse;
import com.team01.airdnb.user.User;
import com.team01.airdnb.user.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

  private Reservation findById(Long reservationId) {
    return reservationRepository.findById(reservationId)
        .orElseThrow(() -> new NoSuchElementException("존재하지 않는 예약입니다."));
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
    Reservation reservation = findById(reservationId);

    if (checkAvailability(reservationRequest)) {
      throw new IllegalArgumentException("예약이 불가능합니다.");
    }

    reservation.update(reservationRequest);
  }

  @Transactional(readOnly = true)
  public ReservationShowResponse showReservation(Long reservationId) {
    Reservation reservation = findById(reservationId);

    return toReservationShowResponse(reservation);
  }

  public ReservationShowResponse toReservationShowResponse(Reservation reservation) {
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

  @Transactional(readOnly = true)
  public List<ReservationShowResponse> findAll (){
    List<Reservation> reservations = reservationRepository.findAll();
    List<ReservationShowResponse> reservationShowResponses = new ArrayList<>();

    for (Reservation reservation : reservations) {
      reservationShowResponses.add(toReservationShowResponse(reservation));
    }
    return reservationShowResponses;
  }

  @Transactional(readOnly = true)
  public List<ReservationHostListResponse> getOwnedAccommodationReservation(Authentication authentication) {
    User user = userService.findByEmail(authentication.getName())
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    List<AccommodationHostResponse> accommodations = accommodationService.getAccommodationByUserId(user.getId());
    List<ReservationHostListResponse> reservationHostListResponses = new ArrayList<>();

    for (AccommodationHostResponse accommodation : accommodations) {
      List<Reservation> reservations = reservationRepository.findAllByAccommodationIdAndHostId(accommodation.id(), user.getId());
      List<ReservationShowResponse> reservationShowResponses = reservations.stream()
          .map(this::toReservationShowResponse)
          .collect(Collectors.toList());
      reservationHostListResponses.add(new ReservationHostListResponse(accommodation, reservationShowResponses));
    }

    return reservationHostListResponses;
  }
}
