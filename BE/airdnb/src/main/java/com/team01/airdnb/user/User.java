package com.team01.airdnb.user;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
}
