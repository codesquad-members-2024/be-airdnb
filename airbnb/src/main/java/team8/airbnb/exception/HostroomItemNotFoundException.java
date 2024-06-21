package team8.airbnb.exception;

public class HostroomItemNotFoundException extends RuntimeException {
  public HostroomItemNotFoundException(String message) {
    super(message);
  }
}
