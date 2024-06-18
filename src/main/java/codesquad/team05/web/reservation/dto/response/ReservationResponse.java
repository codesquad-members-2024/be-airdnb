package codesquad.team05.web.reservation.dto.response;

import codesquad.team05.web.accommodation.dto.response.AccommodationResponse;
import codesquad.team05.web.user.dto.response.UserResponse;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationResponse {

    private UserResponse user;
    private AccommodationResponse accommodationId;
    private LocalDateTime registeredAt;
    private int amount;
    private int personCount;
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate checkIn;
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate checkOut;

}
