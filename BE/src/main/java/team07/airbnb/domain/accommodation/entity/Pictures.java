package team07.airbnb.domain.accommodation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "ACCOMMODATION_PICTURE")
public class Pictures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "accommodation_id")
    private Long accommodationId;

    private String url;

    public Pictures(Long accommodationId, String url) {
        this.accommodationId = accommodationId;
        this.url = url;
    }
}
