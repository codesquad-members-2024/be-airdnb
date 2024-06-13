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
public class AccommodationFee {

    @Column(name = "day_rate", nullable = false)
    private Integer dayRate;

    @Column(name = "cleaning_fee", nullable = false)
    private Integer cleaningFee;
}
