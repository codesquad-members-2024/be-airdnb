package com.team01.airdnb.accommadation;

import com.team01.airdnb.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Reservation {
  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User userId;
  @ManyToOne
  @JoinColumn(name = "accommodation_id")
  private Accommodation accommodation;
  private LocalDate startDate;
  private LocalDate endDate;
  private Integer adults;
  private Integer children;
  private Integer infants;
  private Integer pets;
  private Long price;
}
