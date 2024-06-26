package codesquad.team05.domain.accommodation.accommodationhashtag;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccommodationHashtagId implements Serializable {

    private Long accommodationId;
    private Long hashtagId;

    public AccommodationHashtagId(Long accommodationId, Long hashtagId) {
        this.accommodationId = accommodationId;
        this.hashtagId = hashtagId;
    }
}