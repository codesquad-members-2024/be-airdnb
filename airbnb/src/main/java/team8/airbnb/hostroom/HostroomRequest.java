package team8.airbnb.hostroom;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HostroomRequest {
  private String hostroomName;
  private int bedNumber;
  private int restroomNumber;
  private int bathroomNumber;
  private String region;
  private int limitedAdults;
  private int limitedChildren;
  private int limitedInfants;
  private int limitedPet;
  private boolean isPet;
  private boolean isInstantbook;
  private boolean isSelfcheckin;
  private int price;
  private LocalDateTime checkinDate;
  private LocalDateTime checkoutDate;
}
