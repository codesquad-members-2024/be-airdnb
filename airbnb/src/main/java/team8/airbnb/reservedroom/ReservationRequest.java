package team8.airbnb.reservedroom;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationRequest {
  private LocalDateTime checkinDate;
  private LocalDateTime checkoutDate;
  private int adults;
  private int children;
  private int infants;
}