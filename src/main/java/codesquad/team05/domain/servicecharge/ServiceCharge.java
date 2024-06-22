package codesquad.team05.domain.servicecharge;

import codesquad.team05.domain.accommodation.Accommodation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ServiceCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceType serviceType;
    @Column(nullable = false)
    private Integer fee;
    private Integer intervalDays;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;


    public ServiceCharge(ServiceType serviceType, Integer fee, Integer intervalDays) {
        this.serviceType = serviceType;
        this.fee = fee;
        this.intervalDays = intervalDays;
    }

    public int calculateFee(int days) {
        double weeks = days / 7.0;

        return (int) Math.round(weeks) * fee;
    }
}
