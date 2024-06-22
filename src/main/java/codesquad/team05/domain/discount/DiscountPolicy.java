package codesquad.team05.domain.discount;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDate;

@Entity
@Getter
@BatchSize(size = 20)
public class DiscountPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double discountRate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long accommodationId;


    public DiscountPolicy() {
    }


    public DiscountPolicy(Double discountRate
            , LocalDate startDate
            , LocalDate endDate
            , Long accommodationId) {

        this.discountRate=discountRate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accommodationId = accommodationId;
    }

    public boolean isStartDate() {
        return this.startDate.isEqual(LocalDate.now());
    }

}
