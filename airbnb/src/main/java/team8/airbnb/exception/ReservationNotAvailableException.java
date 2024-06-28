package team8.airbnb.exception;

public class ReservationNotAvailableException extends RuntimeException {
  public ReservationNotAvailableException(String message) {
    super(message);
  }
}