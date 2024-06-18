package com.airdnb.clone.domain.booking.request;

import com.airdnb.clone.domain.common.Guest;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class BookingUpdateRequest {

    @Min(value = 1)
    private final Integer guestCount;

    public Guest toGuest() {
        return Guest.builder()
                .guestCount(guestCount)
                .build();
    }
}
