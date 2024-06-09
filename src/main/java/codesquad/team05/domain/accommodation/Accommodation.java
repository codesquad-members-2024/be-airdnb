package codesquad.team05.domain.accommodation;

import codesquad.team05.domain.reservation.Reservation;
import codesquad.team05.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

    @JsonIgnore
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    @Builder
    public Accommodation(String name, int price, String address, int maxCapacity, int roomCount, int bedCount, String description, User user) {
        this.name = name;
        this.price = price;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.roomCount = roomCount;
        this.bedCount = bedCount;
        this.description = description;
        this.user = user;
    }
}
