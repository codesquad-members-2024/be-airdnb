package team8.airbnb.user;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
}