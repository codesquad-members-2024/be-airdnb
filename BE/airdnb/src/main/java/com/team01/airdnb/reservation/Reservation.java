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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
  @NotNull(message = "예약하기 위해서는 어른 한명이 필요합니다")
  @Min(value = 1, message = "예약하기 위해서는 어른 한명이 필요합니다")
  private Integer adults = 1;
  private Integer children = 0;
  private Integer infants = 0;
  private Integer pets = 0;
  private Long price;
  @NotNull(message = "시작 날짜가 필요합니다")
  private LocalDate startDate;
  @NotNull(message = "끝 날짜가 필요합니다")
  private LocalDate endDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "accommodation_id")
  private Accommodation accommodation;
}
