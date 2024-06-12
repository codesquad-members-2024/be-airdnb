package codesquad.airdnb.domain.accommodation.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ReservationProductId.class)
@Table(name = "RESERVATION_PRODUCT")
public class ReservationProduct {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESERVE_ID")
    private Reservation reservation;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private AccoProduct accoProduct;
}
