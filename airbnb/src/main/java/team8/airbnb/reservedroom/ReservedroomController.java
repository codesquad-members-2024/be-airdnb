package team8.airbnb.reservedroom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import team8.airbnb.exception.ReservationNotAvailableException;

@RestController
@RequestMapping("/api/reservations")
public class ReservedroomController {

  @Autowired
  private ReservedroomService reservedroomService;

  @GetMapping
  public ResponseEntity<List<Reservedroom>> getAllReservations() {
    return ResponseEntity.ok(reservedroomService.getAllReservations());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Reservedroom> getReservationById(@PathVariable Long id) {
    return ResponseEntity.ok(reservedroomService.getReservationById(id));
  }

  @PostMapping("/hostroom/{hostroomId}/user/{userId}")
  public ResponseEntity<Reservedroom> createReservation(
      @PathVariable Long hostroomId,
      @PathVariable Long userId,
      @RequestBody ReservationRequest request) {
    return ResponseEntity.ok(reservedroomService.createReservation(hostroomId, userId, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> cancelReservation(@PathVariable Long id) {
    reservedroomService.cancelReservation(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/hostroom/{hostroomId}")
  public ResponseEntity<List<Reservedroom>> getReservationsByHostroom(@PathVariable Long hostroomId) {
    return ResponseEntity.ok(reservedroomService.getReservationsByHostroom(hostroomId));
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Reservedroom>> getReservationsByGuest(@PathVariable Long userId) {
    return ResponseEntity.ok(reservedroomService.getReservationsByGuest(userId));
  }
  @ExceptionHandler(ReservationNotAvailableException.class)
  public ResponseEntity<String> handleReservationNotAvailable(ReservationNotAvailableException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }
}