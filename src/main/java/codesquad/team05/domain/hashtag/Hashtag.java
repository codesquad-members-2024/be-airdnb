package codesquad.team05.domain.hashtag;

import codesquad.team05.domain.accommodation.Accommodation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    public Hashtag(String content, Accommodation accommodation) {
        this.content = content;
        if (accommodation != null) {
            accommodation.getHashtags().remove(this);
        }
        this.accommodation = accommodation;
        accommodation.getHashtags().add(this);
    }
}
