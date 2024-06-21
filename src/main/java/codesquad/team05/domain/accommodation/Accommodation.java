package codesquad.team05.domain.accommodation;

import codesquad.team05.domain.hashtag.Hashtag;
import codesquad.team05.domain.host.Host;
import codesquad.team05.domain.like.Like;
import codesquad.team05.domain.picture.Picture;
import codesquad.team05.domain.reservation.Reservation;
import codesquad.team05.domain.review.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
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
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private int maxCapacity;
    @Column(nullable = false)
    private int roomCount;
    @Column(nullable = false)
    private int bedCount;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String amenity;

    @ManyToOne(fetch = FetchType.LAZY)
    private Host host;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
    List<Reservation> reservation = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
    List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<Hashtag> hashtags = new ArrayList<>();

    public Accommodation(
            String name,
            int price,
            String address,
            int maxCapacity,
            int roomCount,
            int bedCount,
            String description,
            String amenity
    ) {
        this.name = name;
        this.price = price;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.roomCount = roomCount;
        this.bedCount = bedCount;
        this.description = description;
        this.amenity = amenity;
    }

    public void update(
            String name,
            int price,
            String address,
            int maxCapacity,
            int roomCount,
            int bedCount,
            String description,
            String amenity
    ) {
        this.name = name;
        this.price = price;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.roomCount = roomCount;
        this.bedCount = bedCount;
        this.description = description;
        this.amenity = amenity;
    }
}
