package codesquad.team05.domain.hastag;

import codesquad.team05.domain.accomodation.Accommodation;
import jakarta.persistence.*;

@Entity
public class Hastag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;
}
