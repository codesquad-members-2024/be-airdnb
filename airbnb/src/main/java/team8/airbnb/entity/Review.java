package team8.airbnb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "hostroom_id")
  private Hostroom hostroom;

  @ManyToOne
  @JoinColumn(name = "guest_id")
  private User user;

  @Column(name = "context")
  private String content;

  @Column(name = "rating")
  private int rating;
}
