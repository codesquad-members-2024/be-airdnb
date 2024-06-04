package codesquad.airdnb.domain.accommodation;

import jakarta.persistence.*;

@Entity
@Table(name = "AMENITY")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
