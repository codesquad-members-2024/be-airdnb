package com.team01.airdnb.accommadation;

import com.team01.airdnb.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accommodation")
public class Accommodation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userId;
  private String title;
  private String content;
  private Long price;
  private Integer discount;
  private Integer commentsNum;
  private Integer maxAdults;
  private Integer maxChildren;
  private Integer maxInfants;
  private Integer maxPets;
  private String address;
  private Double latitude;
  private Double longitude;
  @OneToMany(mappedBy = "accommodation")
  private List<Comment> comments;
  @OneToOne(mappedBy = "accommodation", cascade = CascadeType.ALL)
  private Amenity amenity;
  @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Image> images = new ArrayList<>();
}
