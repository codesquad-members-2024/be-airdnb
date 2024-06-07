package com.team01.airdnb.reservation;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "reservations")
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer adults;
  private Integer children;
  private Integer infants;
  private Integer pets;
  private Long price;
  private LocalDate startDate;
  private LocalDate endDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "accommodation_id")
  private Accommodation accommodation;
}
