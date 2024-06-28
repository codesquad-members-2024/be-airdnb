package team8.airbnb.reservedroom;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team8.airbnb.hostroom.Hostroom;
import team8.airbnb.hostroom.HostroomRepository;
import team8.airbnb.user.User;
import team8.airbnb.user.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
public class ReservedroomService {

  @Autowired
  private ReservedroomRepository reservedroomRepository;

  @Autowired
  private HostroomRepository hostroomRepository;

  @Autowired
  private UserRepository userRepository;

  public List<Reservedroom> getAllReservations() {
    return reservedroomRepository.findAll();
  }

  public Reservedroom getReservationById(Long id) {
    return reservedroomRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Reservation not found"));
  }

  @Transactional
  public Reservedroom createReservation(Long hostroomId, Long userId, ReservationRequest request) {
    Hostroom hostroom = hostroomRepository.findById(hostroomId)
        .orElseThrow(() -> new RuntimeException("Hostroom not found"));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    log.info("Checking availability for hostroom {} from {} to {}",
        hostroomId, request.getCheckinDate(), request.getCheckoutDate());

    if (!isAvailable(hostroomId, request.getCheckinDate(), request.getCheckoutDate())) {
      log.warn("Hostroom {} is not available for the selected dates", hostroomId);
      throw new RuntimeException("The hostroom is not available for the selected dates");
    }

    if (request.getAdults() > hostroom.getLimitedAdults() ||
        request.getChildren() > hostroom.getLimitedChildren() ||
        request.getInfants() > hostroom.getLimitedInfants()) {
      throw new RuntimeException("The number of guests exceeds the hostroom's limit");
    }

    long days = ChronoUnit.DAYS.between(request.getCheckinDate(), request.getCheckoutDate());
    int totalPrice = (int) (hostroom.getPrice() * days);

    Reservedroom reservedroom = new Reservedroom();
    reservedroom.setGuest(user);
    reservedroom.setHostroom(hostroom);
    reservedroom.setAdults(request.getAdults());
    reservedroom.setChildren(request.getChildren());
    reservedroom.setInfants(request.getInfants());
    reservedroom.setCheckinDate(request.getCheckinDate());
    reservedroom.setCheckoutDate(request.getCheckoutDate());
    reservedroom.setTotalPrice(totalPrice);

    return reservedroomRepository.save(reservedroom);
  }

  public boolean isAvailable(Long hostroomId, LocalDateTime startDate, LocalDateTime endDate) {
    log.info("Checking availability for hostroom {} from {} to {}", hostroomId, startDate, endDate);

    Hostroom hostroom = hostroomRepository.findById(hostroomId)
        .orElseThrow(() -> new RuntimeException("Hostroom not found"));

    log.info("Hostroom {} available period: {} to {}", hostroomId, hostroom.getCheckinDate(), hostroom.getCheckoutDate());

    List<Reservedroom> overlappingReservations = reservedroomRepository.findOverlappingReservations(hostroomId, startDate, endDate);
    log.info("Found {} overlapping reservations", overlappingReservations.size());

    return overlappingReservations.isEmpty();
  }
  @Transactional
  public void cancelReservation(Long id) {
    Reservedroom reservedroom = getReservationById(id);
    reservedroomRepository.delete(reservedroom);
  }

  public List<Reservedroom> getReservationsByHostroom(Long hostroomId) {
    return reservedroomRepository.findByHostroomId(hostroomId);
  }

  public List<Reservedroom> getReservationsByGuest(Long guestId) {
    return reservedroomRepository.findByGuestId(guestId);
  }
}