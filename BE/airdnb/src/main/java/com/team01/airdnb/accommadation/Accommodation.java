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
  private String title;
  private String content;
  private Long price;
  private Integer discount;
  private String address;
  private Double latitude;
  private Double longitude;
  private Integer commentsNum;
  private Integer maxAdults;
  private Integer maxChildren;
  private Integer maxInfants;
  private Integer maxPets;

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
