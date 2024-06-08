package team03.airdnb.accommodation;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Address {

    private String firstAddress; // 광역시/도
    private String secondAddress; // 시/군/구
    private String thirdAddress; // 도로명
}
