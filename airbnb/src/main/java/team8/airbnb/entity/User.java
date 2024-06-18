package team8.airbnb.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Entity
@Data
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_name", unique = true)
  private String username;

  @Column(name = "user_password")
  private String password;

  @Column(name = "user_email", unique = true)
  private String email;

  @Column(name = "user_phone_number", unique = true)
  private String phoneNumber;

  @Column(name = "role")
  private String role;

  @Column(name = "oauth_type")
  private String oauthType;

  // hostroom에 매핑될 host 필드
  @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Hostroom> hostrooms = new ArrayList<>();

  // reversedroom
  @OneToMany(mappedBy = "guest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Reservedroom> reservedrooms = new ArrayList<>();

}