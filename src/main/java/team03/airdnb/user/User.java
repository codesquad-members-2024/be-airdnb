package team03.airdnb.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.common.BaseEntity;
import team03.airdnb.favorite.Favorite;
import team03.airdnb.reservation.Reservation;
import team03.airdnb.review.Review;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;
    private String profileImg;
    private String type;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Favorite> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private List<Accommodation> accommodations = new ArrayList<>();
}
