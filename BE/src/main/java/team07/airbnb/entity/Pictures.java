package team07.airbnb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACCOMMODATION_PICTURE")
@NoArgsConstructor
@Getter
public class Pictures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private AccommodationEntity accommodation;

    private String url;

    public Pictures(AccommodationEntity accommodation, String url) {
        this.accommodation = accommodation;
        this.url = url;
    }

    public void attach(AccommodationEntity accommodation) {
        this.accommodation = accommodation;
    }

    public void detach() {
        this.accommodation = null;
    }
}
