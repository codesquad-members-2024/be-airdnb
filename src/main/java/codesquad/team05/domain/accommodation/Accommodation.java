package codesquad.team05.domain.accommodation;

import codesquad.team05.domain.accommodation.like.Like;
import codesquad.team05.domain.accommodation.reservation.Reservation;
import codesquad.team05.domain.accommodation.reservation.review.Review;
import codesquad.team05.domain.hashtag.Hashtag;
import codesquad.team05.domain.picture.Picture;
import codesquad.team05.web.dto.request.accommodation.AccommodationUpdate;
import codesquad.team05.web.dto.response.accommodation.AccommodationResponse;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
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
    private Long hostId;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    List<Reservation> reservation = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    List<Hashtag> hashtags = new ArrayList<>();

    public Accommodation(String name, int price, String address, int maxCapacity, int roomCount, int bedCount, String description, String amenity, Long hostId) {
        this.name = name;
        this.price = price;
        this.address = address;
        this.maxCapacity = maxCapacity;
        this.roomCount = roomCount;
        this.bedCount = bedCount;
        this.description = description;
        this.amenity = amenity;
        this.hostId = hostId;
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

        return accommodationResponse;
    }
}
