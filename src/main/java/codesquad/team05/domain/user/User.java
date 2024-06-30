package codesquad.team05.domain.user;

import codesquad.team05.domain.coupon.UserCoupon;
import codesquad.team05.domain.host.Host;
import codesquad.team05.domain.like.Like;
import codesquad.team05.domain.reservation.Reservation;
import codesquad.team05.domain.review.Review;
import codesquad.team05.web.user.dto.response.UserResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static codesquad.team05.domain.user.Role.*;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String loginId;
    private String name;
    private String password;
    private String address;
    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    private Role role = USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Reservation> reservation = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Like> likes = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Host host;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<UserCoupon> coupons = new ArrayList<>();

    public User(String loginId, String name, String password, String address, LocalDate birthdate) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.address = address;
        this.birthdate = birthdate;
    }

    public void enrollHost() {
        if (this.host == null) {
            Host host = new Host();
            host.setUser(this);
            this.host = host;
            this.role = HOST;
        }
    }

    public void setOAuth2UserInfo(String email, String name){
        this.loginId = email;
        this.name = name;
    }

    public void updateOAuth2User(String name){
        this.name = name;
    }

    public UserResponse toEntity() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(id);
        userResponse.setName(name);
        userResponse.setLoginId(loginId);

        return userResponse;
    }

    public void setRoleAsGuest(){
        this.role = GUEST;
    }

    public boolean isGuest(){
        return this.role.equals(GUEST);
    }
}
