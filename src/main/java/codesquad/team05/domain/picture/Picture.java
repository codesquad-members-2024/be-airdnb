package codesquad.team05.domain.picture;

import codesquad.team05.domain.accommodation.Accommodation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    public Picture(String url, Accommodation accommodation) {
        this.url = url;
        if (this.accommodation != null) {
            accommodation.getPictures().remove(this);
        }
        this.accommodation = accommodation;
        accommodation.getPictures().add(this);
    }
}
