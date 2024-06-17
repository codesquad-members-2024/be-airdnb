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
public class Address {

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "neighborhood", nullable = false) // 동
    private String neighborhood;

    @Column(name = "street_name", nullable = false) //도로명 주소
    private String streetName;

    @Column(name = "detailed_address", nullable = false)
    private String detailedAddress;

}
