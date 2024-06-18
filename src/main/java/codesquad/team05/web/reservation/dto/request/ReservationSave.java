package codesquad.team05.web.reservation.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationSave {

    @NotBlank
    private LocalDateTime registeredAt;

    @NotBlank
    private int personCount;

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate checkIn;

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate checkOut;

    public ReservationServiceDto toServiceDto() {
        ReservationServiceDto reservationServiceDto = new ReservationServiceDto();
        reservationServiceDto.setPersonCount(this.personCount);
        reservationServiceDto.setCheckIn(this.checkIn);
        reservationServiceDto.setCheckOut(this.checkOut);
        reservationServiceDto.setRegisteredAt(LocalDateTime.now());
        return reservationServiceDto;

    }
}
