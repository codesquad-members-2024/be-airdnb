package team8.airbnb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "reservedroom")
public class Reservedroom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "guest_id")
  private User guest;

  @Column(name = "adults")
  private int adults;

  @Column(name = "children")
  private int children;

  @Column(name = "infants")
  private int infants;

  @Column(name = "total_price")
  private int totalPrice;

  @Column(name = "checkin_date")
  private LocalDateTime checkinDate;

  @Column(name = "checkout_date")
  private LocalDateTime checkoutDate;

  // hostroom_id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hostroom_id")
  private Hostroom hostroom;
}
