package team03.airdnb.reservation;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "RESERVATIONS")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
