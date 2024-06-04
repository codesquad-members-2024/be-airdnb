package team07.airbnb.domain.review;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import team07.airbnb.domain.BaseEntity;
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.booking.BookingEntity;

@Entity
@Table(name = "REVIEW")
public class ReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private UserEntity writer;
    @OneToOne
    private BookingEntity booking;
    private String content;
    private int score;
}
