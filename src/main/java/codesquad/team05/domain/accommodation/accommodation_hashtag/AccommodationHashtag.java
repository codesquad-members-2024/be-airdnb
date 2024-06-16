package codesquad.team05.domain.accommodation.accommodation_hashtag;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.hashtag.Hashtag;
import jakarta.persistence.*;

@Entity
public class AccommodationHashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;
}
