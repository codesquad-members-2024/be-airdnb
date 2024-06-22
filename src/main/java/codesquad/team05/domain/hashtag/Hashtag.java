package codesquad.team05.domain.hashtag;

import codesquad.team05.domain.accommodation.Accommodation;
import jakarta.persistence.*;

@Entity
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;
}
