package team07.airbnb.domain.booking;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import team07.airbnb.domain.BaseEntity;

@Entity
@Table(name = "DISCOUNT_POLICY")
public class DiscountPolicy extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;
}
