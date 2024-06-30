package codesquad.team05.domain.accommodation;

import codesquad.team05.domain.accommodation.accommodationhashtag.AccommodationHashtag;
import codesquad.team05.domain.host.Host;
import codesquad.team05.domain.like.Like;
import codesquad.team05.domain.picture.Picture;
import codesquad.team05.domain.reservation.Reservation;
import codesquad.team05.domain.servicecharge.ServiceCharge;
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
    private boolean isOnSale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Host host;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccommodationType accommodationType;

    @OneToMany(mappedBy = "accommodation")
    private List<Reservation> reservation = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.REMOVE)
    private List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<AccommodationHashtag> accommodationHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ServiceCharge> serviceCharge = new ArrayList<>();

    public Accommodation(
            String name,
            int price,
            String address,
            int maxCapacity,
            int roomCount,
            int bedCount,
            String description,
            String amenity,
            AccommodationType accommodationType
    ) {
        this.name = name;
        this.price = price;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.roomCount = roomCount;
        this.bedCount = bedCount;
        this.description = description;
        this.amenity = amenity;
        this.accommodationType = accommodationType;
    }

    public void update(
            String name,
            int price,
            String address,
            int maxCapacity,
            int roomCount,
            int bedCount,
            String description,
            String amenity,
            AccommodationType accommodationType
    ) {
        this.name = name;
        this.price = price;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.roomCount = roomCount;
        this.bedCount = bedCount;
        this.description = description;
        this.amenity = amenity;
        this.accommodationType = accommodationType;
    }

    public void startDiscount() {
        this.isOnSale = true;
    }

    public void addServiceCharge(ServiceCharge serviceCharge) {
        this.serviceCharge.add(serviceCharge);
    }

    public void addAccommodationHashtag(AccommodationHashtag accommodationHashtag) {
        accommodationHashtags.add(accommodationHashtag);
    }
}
