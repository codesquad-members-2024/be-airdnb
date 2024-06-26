package codesquad.team05.domain.accommodation.accommodationhashtag;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.hashtag.Hashtag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccommodationHashtag {

    @EmbeddedId
    private AccommodationHashtagId accommodationHashtagId;

    @MapsId("accommodationId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    @MapsId("hashtagId")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;

    public AccommodationHashtag(Accommodation accommodation, Hashtag hashtag) {
        this.accommodation = accommodation;
        this.hashtag = hashtag;
        accommodationHashtagId = new AccommodationHashtagId(accommodation.getId(), hashtag.getId());
        accommodation.getAccommodationHashtags().add(this);
    }
}
