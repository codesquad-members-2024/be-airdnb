package com.team01.airdnb.accommadation;

import com.team01.airdnb.amenity.Amenity;
import com.team01.airdnb.comment.Comment;
import com.team01.airdnb.host.Host;
import com.team01.airdnb.image.Image;
import com.team01.airdnb.reservation.Reservation;
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
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "accommodations")
public class Accommodation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "제목을 입력하세요")
  private String title;
  private String content;
  @NotBlank(message = "가격을 입력하세요")
  private Long price;
  private Integer discount = 0;
  @NotBlank(message = "주소를 입력하세요")
  private String address;
  private Double latitude;
  private Double longitude;
  private Integer commentsNum = 0;
  @NotBlank(message = "등록하기 위해서는 어른 한명이 필요합니다")
  @Min(value = 1, message = "등록하기 위해서는 어른 한명이 필요합니다")
  private Integer maxAdults = 1;
  private Integer maxChildren = 0;
  private Integer maxInfants = 0;
  private Integer maxPets = 0;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "host_id")
  private Host host;

  @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Image> images;

  @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reservation> reservations;

  @OneToOne(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Amenity amenity;
}
