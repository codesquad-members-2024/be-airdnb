package codesquad.team05.domain.reservation;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.user.User;
import codesquad.team05.util.AccommodationMapper;
import codesquad.team05.web.reservation.dto.request.ReservationUpdate;
import codesquad.team05.web.reservation.dto.response.ReservationResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version()
    private long version;
    @Column(nullable = false)
    private LocalDateTime registeredAt;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private int personCount;
    @Column(nullable = false)
    private LocalDate checkIn;
    @Column(nullable = false)
    private LocalDate checkOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    public ReservationResponse toEntity() {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setUser(user.toEntity());
        reservationResponse.setAccommodationId(AccommodationMapper.toResponse(accommodation));
        reservationResponse.setRegisteredAt(registeredAt);
        reservationResponse.setAmount(amount);
        reservationResponse.setPersonCount(personCount);
        reservationResponse.setCheckIn(checkIn);
        reservationResponse.setCheckOut(checkOut);

        return reservationResponse;
    }


    public void update(ReservationUpdate reservationUpdate) {
        this.amount = reservationUpdate.getAmount();
        this.personCount = reservationUpdate.getPersonCount();
        this.checkIn = reservationUpdate.getCheckIn();
        this.checkOut = reservationUpdate.getCheckOut();
    }
}
