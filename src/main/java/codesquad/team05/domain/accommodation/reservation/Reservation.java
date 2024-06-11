package codesquad.team05.domain.accommodation.reservation;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
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
}
