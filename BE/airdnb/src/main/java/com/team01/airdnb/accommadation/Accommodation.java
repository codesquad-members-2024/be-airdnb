package com.team01.airdnb.accommadation;

import com.team01.airdnb.accommadation.dto.AccommodationUpdateRequest;
import com.team01.airdnb.amenity.Amenity;
import com.team01.airdnb.comment.Comment;
import com.team01.airdnb.image.Image;
import com.team01.airdnb.reservation.Reservation;
import com.team01.airdnb.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accommodations")
public class Accommodation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank(message = "제목을 입력하세요")
  private String title;
  private String content;
  @NotNull(message = "가격을 입력하세요")
  private Long price;
  @Builder.Default
  private Integer discountRate = 0;
  @NotBlank(message = "주소를 입력하세요")
  private String address;
  private Double latitude;
  private Double longitude;
  @Builder.Default
  private Integer commentsNum = 0;
  @NotNull(message = "등록하기 위해서는 어른 한명이 필요합니다")
  @Min(value = 1, message = "등록하기 위해서는 어른 한명이 필요합니다")
  @Builder.Default
  private Integer maxAdults = 1;
  @Builder.Default
  private Integer maxChildren = 0;
  @Builder.Default
  private Integer maxInfants = 0;
  @Builder.Default
  private Integer maxPets = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Image> images;

  @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reservation> reservations;

  @OneToOne(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Amenity amenity;

  /**
   * 숙소와 어메니티의 관계를 양쪽 모두 연결합니다.
   *
   * @param amenity
   */
  public void setAmenityMapping(Amenity amenity) {
    this.amenity = amenity;
    amenity.setAccommodation(this);
  }

  public void setImageMapping(List<Image> images) {
    this.images = images;
    images.forEach(image -> image.setAccommodation(this));
  }

  public void update(AccommodationUpdateRequest accommodationUpdateRequest) {
    this.title = accommodationUpdateRequest.title();
    this.content = accommodationUpdateRequest.content();
    this.price = accommodationUpdateRequest.price();
    this.discountRate = accommodationUpdateRequest.discountRate();
    this.maxAdults = accommodationUpdateRequest.maxAdults();
    this.maxChildren = accommodationUpdateRequest.maxChildren();
    this.maxInfants = accommodationUpdateRequest.maxInfants();
    this.maxPets = accommodationUpdateRequest.maxPets();
    this.address = accommodationUpdateRequest.address();
  }
}
