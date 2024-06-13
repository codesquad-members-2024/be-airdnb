package com.airdnb.clone.domain.stay.controller.dto.response.edit;

import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckInOutTimeEditResponse {

    private final LocalTime checkInTime;
    private final LocalTime checkOutTime;

    public static CheckInOutTimeEditResponse of(LocalTime checkInTime, LocalTime checkOutTime) {
        return new CheckInOutTimeEditResponse(checkInTime, checkOutTime);
    }
}
