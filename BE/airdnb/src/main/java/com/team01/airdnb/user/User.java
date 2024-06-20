package com.team01.airdnb.user;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.comment.Comment;
import com.team01.airdnb.host.Host;
import com.team01.airdnb.reservation.Reservation;
import com.team01.airdnb.wishlist.Wishlist;
import jakarta.persistence.*;
import java.util.List;

import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String email;
  private String profile;

  @Enumerated(value = EnumType.STRING)
  private Role role;

  @Enumerated(value = EnumType.STRING)
  private SocialType socialType;

  private String socialId;

  //연관 관계
  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private Host host;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reservation> reservations;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Wishlist> wishlists;

  @OneToMany(mappedBy = "user")
  private List<Comment> comments;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Accommodation> accommodations;
}
