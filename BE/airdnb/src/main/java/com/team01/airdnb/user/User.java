package com.team01.airdnb.user;

import com.team01.airdnb.comment.Comment;
import com.team01.airdnb.host.Host;
import com.team01.airdnb.reservation.Reservation;
import com.team01.airdnb.wishlist.Wishlist;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
  @Id
  public String id;
  public String username;
  public String password;
  @Enumerated(value = EnumType.STRING)
  public Role role;
  public int age;

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Host host;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Reservation> reservations;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Wishlist> wishlists;

  @OneToMany(mappedBy = "user")
  private List<Comment> comments;
}
