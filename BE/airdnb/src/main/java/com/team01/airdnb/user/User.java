package com.team01.airdnb.user;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.comment.Comment;
import com.team01.airdnb.host.Host;
import com.team01.airdnb.reservation.Reservation;
import com.team01.airdnb.wishlist.Wishlist;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
  @Id
  @NotBlank(message = "아이디를 입력하세요")
  @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하여야 합니다")
  public String id;

  @NotBlank(message = "이름을 입력하세요")
  @Size(min = 1, max = 10, message = "이름은 1자 이상 10자 이하여야 합니다")
  public String username;

  @NotBlank(message = "비밀번호를 입력하세요")
  @Size(min = 6, max = 20, message = "비밀번호는 6자 이상 20자 이하여야 합니다")
  public String password;

  @Enumerated(value = EnumType.STRING)
  public Role role;

  @NotNull(message = "나이를 입력하세요")
  @Max(value = 100, message = "나이는 100 이하여야 합니다")
  public Integer age;

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
