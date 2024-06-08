package codesquad.team05.domain.reservation;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;
    private int amount;
    private int personCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @Builder
    public Reservation(int amount, int personCount, LocalDate checkInDate, LocalDate checkOutDate, User user, Accommodation accommodation) {
        this.amount = amount;
        this.personCount = personCount;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.user = user;
        this.accommodation = accommodation;
    }
}
