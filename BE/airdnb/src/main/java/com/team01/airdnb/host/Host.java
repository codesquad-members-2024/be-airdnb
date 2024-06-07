package com.team01.airdnb.host;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;

@Entity
public class Host {
  @Id
  @OneToOne
  @JoinColumn(name = "user_id")
  private User id;
  private int accountNum;

  @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Accommodation> accommodations;
}
