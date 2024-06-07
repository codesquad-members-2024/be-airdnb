package com.team01.airdnb.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Host {
  @Id
  @OneToOne
  @JoinColumn(name = "user_id")
  private User id;
  private int accountNum;
}
