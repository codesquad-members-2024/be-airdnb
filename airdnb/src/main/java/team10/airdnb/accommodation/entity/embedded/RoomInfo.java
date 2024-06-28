package team10.airdnb.accommodation.entity.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Embeddable
public class RoomInfo {

    @Column(name = "bedroom_count", nullable = false)
    private Integer bedroomCount;

    @Column(name = "bathroom_count", nullable = false)
    private Integer bathroomCount;

    @Column(name = "bed_count", nullable = false)
    private Integer bedCount;
}
