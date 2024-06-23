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
public class Coordinate {

    @Column(name = "latitude", nullable = false)
    private Double latitude; // 위도

    @Column(name = "longitude", nullable = false)
    private Double longitude; //경도

}
