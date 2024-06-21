package team8.airbnb.hostroom;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import team8.airbnb.hostroomitems.HostroomItems;
import team8.airbnb.review.Review;

@Getter
@Setter
@JsonPropertyOrder({
    "id", "hostroomName", "bedNumber", "restroomNumber", "bathroomNumber", "region",
    "limitedAdults", "limitedChildren", "limitedInfants", "limitedPet", "price",
    "checkinDate", "checkoutDate", "latitude", "longitude",
    "selfcheckin", "reserved", "instantbook", "pet", "hostroomItems", "reviews"
})
public class HostroomResponse {
  private Long id;
  private String hostroomName;
  private int bedNumber;
  private int restroomNumber;
  private int bathroomNumber;
  private String region;
  private int limitedAdults;
  private int limitedChildren;
  private int limitedInfants;
  private int limitedPet;
  private int price;
  private LocalDateTime checkinDate;
  private LocalDateTime checkoutDate;
  private Double latitude;
  private Double longitude;
  private List<HostroomItems> hostroomItems;
  private boolean selfcheckin;
  private boolean reserved;
  private boolean instantbook;
  private boolean pet;
  private List<Review> reviews;
}