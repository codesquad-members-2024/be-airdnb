package team07.airbnb.domain.accommodation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import team07.airbnb.domain.BaseEntity;
import team07.airbnb.domain.accommodation.entity.Pictures;
import team07.airbnb.domain.accommodation.property.AccommodationLocation;
import team07.airbnb.domain.accommodation.property.AccomodationType;
import team07.airbnb.domain.accommodation.property.RoomInformation;
import team07.airbnb.domain.user.UserEntity;

import java.util.List;

@Getter
@Entity
@Table(name = "ACCOMODATION")
public class AccomodationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private UserEntity host;
    private AccomodationType type;

    @Embedded
    private RoomInformation roomInformation;

    @Embedded
    private AccommodationLocation address;

    // Description
    private String name;
    private String description;
    @JsonIgnore
    @OneToMany(mappedBy = "accomodation", fetch = FetchType.LAZY)
    private List<Pictures> images;

    private int basePricePerDay;
}
