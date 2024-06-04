package team07.airbnb.domain.accomodation;

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
import team07.airbnb.domain.accomodation.property.AccommodationLocation;
import team07.airbnb.domain.accomodation.property.AccomodationType;
import team07.airbnb.domain.user.entity.UserEntity;

import java.util.List;

@Getter
@Entity
@Table(name = "ACCOMODATION")
public class AccomodationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne
    private UserEntity host;
    @Embedded
    private AccommodationLocation address;
    private AccomodationType type;

    @JsonIgnore
    @OneToMany(mappedBy = "accomodation", fetch = FetchType.LAZY)
    private List<Pictures> images;
}
