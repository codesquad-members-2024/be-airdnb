package team07.airbnb.domain.accomodation;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import team07.airbnb.domain.User;

@Entity
@Table(name = "ACCOMODATION")
public class AccomodationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne
    private User host;
    @Embedded
    private AccommodationLocation address;
    @Embedded
    private Amenity amenity;
    private AccomodationType type;
}
