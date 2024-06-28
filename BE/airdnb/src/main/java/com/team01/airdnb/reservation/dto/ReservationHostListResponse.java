package com.team01.airdnb.reservation.dto;

import com.team01.airdnb.accommadation.dto.AccommodationHostResponse;
import java.util.List;

public record ReservationHostListResponse(
    AccommodationHostResponse Accommodation,
    List<ReservationShowResponse>  reservations
) {

}
