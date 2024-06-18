package com.airdnb.clone.domain.booking.response;

import com.airdnb.clone.domain.booking.entity.Booking;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingResponse {
    private final Long bookingId;
    private final Long memberId;
    private final Long stayId;
    private final LocalDateTime checkIn;
    private final LocalDateTime checkOut;
    private final Long totalRate;
    private final Integer guestCount;

    public static BookingResponse of(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getMember().getId(),
                booking.getStay().getId(),
                booking.getCheckIn(),
                booking.getCheckOut(),
                booking.getTotalRate(),
                booking.getGuest().getGuestCount()
        );
    }
}
