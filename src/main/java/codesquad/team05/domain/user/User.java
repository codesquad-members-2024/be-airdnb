package codesquad.team05.domain.user;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.accommodation.like.Like;
import codesquad.team05.domain.accommodation.reservation.Reservation;
import codesquad.team05.domain.accommodation.reservation.review.Review;
import codesquad.team05.domain.host.Host;
import codesquad.team05.web.dto.response.user.UserResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String loginId;
    private String name;
    private String password;
    private String nickname;
    private String address;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Accommodation> accommodations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    List<Reservation> reservation = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Like> likes = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Host host;


    public void enrollHost() {
        if (this.host == null) {
            Host host = new Host();
            host.setUser(this);
            this.host = host;
        }


    }

    public UserResponse toEntity() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(id);
        userResponse.setName(name);
        userResponse.setLoginId(loginId);

        return userResponse;
    }

}
