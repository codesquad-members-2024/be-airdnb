package codesquad.airdnb.domain.accommodation.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ReservationProductId implements Serializable {

    public Long reservation;

    public Long accoProduct;
}
