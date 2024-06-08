package team03.airdnb.user;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.like.Like;
import team03.airdnb.reservation.Reservation;
import team03.airdnb.review.Review;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String profileImg;
    private String type;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private List<Accommodation> accommodations = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;
}
