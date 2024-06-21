package team8.airbnb.hostroom;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import team8.airbnb.hostroomitems.HostroomItems;
import team8.airbnb.review.Review;
import team8.airbnb.user.User;

@Entity
@Setter
@Getter
@Table(name = "hostroom")
public class Hostroom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "host_id")
  @JsonBackReference
  private User host;

  @Column(name = "hostroom_name")
  private String hostroomName;

  @Column(name = "bed_number")
  private int bedNumber;

  @Column(name = "restroom_number")
  private int restroomNumber;

  @Column(name = "bathroom_number")
  private int bathroomNumber;

  @Column(name = "region")
  private String region;

  @Column(name = "limited_adults")
  private int limitedAdults;

  @Column(name = "limited_children")
  private int limitedChildren;

  @Column(name = "limited_infants")
  private int limitedInfants;

  @Column(name = "limited_pet")
  private int limitedPet;

  @Column(name = "is_pet")
  private boolean isPet;

  @Column(name = "is_instantbook")
  private boolean isInstantbook;

  @Column(name = "is_selfcheckin")
  private boolean isSelfcheckin;

  @Column(name = "price")
  private int price;

  @Column(name = "checkin_date")
  private LocalDateTime checkinDate;

  @Column(name = "checkout_date")
  private LocalDateTime checkoutDate;

  @Column(name = "is_reserved")
  private boolean isReserved;

  @Column(name = "latitude")
  private Double latitude;

  @Column(name = "longitude")
  private Double longitude;

  @OneToMany(mappedBy = "hostroom", fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<Review> reviews;

  @OneToMany(mappedBy = "hostroom", fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<HostroomItems> hostroomItems;
}
