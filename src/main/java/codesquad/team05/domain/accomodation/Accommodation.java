package codesquad.team05.domain.accomodation;

import codesquad.team05.domain.hastag.Hastag;
import codesquad.team05.domain.like.Likes;
import codesquad.team05.domain.picture.Picture;
import codesquad.team05.domain.reservation.Reservation;
import codesquad.team05.domain.review.Reviews;
import codesquad.team05.domain.servicecharge.ServiceCharge;
import codesquad.team05.web.accommodation.dto.request.AccommodationUpdate;
import codesquad.team05.web.accommodation.dto.response.AccommodationResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter

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

    @Column(nullable = false)
    private Long hostId;

    private boolean isOnSale;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccommodationType accommodationType;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
    List<Reservation> reservation = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
    List<Reviews> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST)
    List<Hastag> hashtags = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<ServiceCharge> serviceCharge = new ArrayList<>();


    public Accommodation(String name
            , int price, String address
            , int maxCapacity
            , int roomCount
            , int bedCount
            , String description
            , String amenity
            , Long hostId
            , AccommodationType accommodationType) {

        this.name = name;
        this.price = price;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.roomCount = roomCount;
        this.bedCount = bedCount;
        this.description = description;
        this.amenity = amenity;
        this.hostId = hostId;
        this.accommodationType = accommodationType;
    }

    public AccommodationResponse toEntity() {

        AccommodationResponse accommodationResponse = new AccommodationResponse();
        accommodationResponse.setId(id);
        accommodationResponse.setName(name);
        accommodationResponse.setPrice(price);
        accommodationResponse.setAddress(address);
        accommodationResponse.setMaxCapacity(maxCapacity);
        accommodationResponse.setRoomCount(roomCount);
        accommodationResponse.setBedCount(bedCount);
        accommodationResponse.setDescription(description);
        accommodationResponse.setAmenity(amenity);
        accommodationResponse.setPictures(pictures.stream().map(Picture::toEntity).toList());
        accommodationResponse.setOnSale(isOnSale);

        return accommodationResponse;
    }

    public void update(AccommodationUpdate newAccommodation) {

        this.name = newAccommodation.getName();
        this.price = newAccommodation.getPrice();
        this.address = newAccommodation.getAddress();
        this.maxCapacity = newAccommodation.getMaxCapacity();
        this.roomCount = newAccommodation.getRoomCount();
        this.bedCount = newAccommodation.getBedCount();
        this.description = newAccommodation.getDescription();
        this.amenity = newAccommodation.getAmenity();
    }

    public void startDiscount() {
        this.isOnSale = true;
    }

    public void addServiceCharge(ServiceCharge serviceCharge) {
        this.serviceCharge.add(serviceCharge);
    }
}
