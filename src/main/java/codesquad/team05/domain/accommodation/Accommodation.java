package codesquad.team05.domain.accommodation;

import codesquad.team05.domain.accommodation.accommodation_hashtag.AccommodationHashtag;
import codesquad.team05.domain.accommodation.reservation.Reservation;
import codesquad.team05.domain.host.Host;
import codesquad.team05.domain.picture.Picture;
import codesquad.team05.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "accommodation_id")
    private Long id;
    private String name;
    private int price;
    private String address;
    private int maxCapacity;
    private int roomCount;
    private int bedCount;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Host host;

    @JsonIgnore
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
    private List<Reservation> reservations = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Picture> pictures = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<AccommodationHashtag> AccommodationHashtags = new ArrayList<>();
}
