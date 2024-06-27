package codesquad.airdnb.domain.accommodation.controller;

import codesquad.airdnb.domain.accommodation.dto.request.AccoReservationRequest;
import codesquad.airdnb.domain.accommodation.service.AccoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/api/guest/accommodations")
@RequiredArgsConstructor
public class GuestAccoController {

    private final AccoService accoService;

    @GetMapping
    public ResponseEntity<?> getAccoByCondition(
            @RequestParam("guestCount") Integer guestCount,
            @RequestParam(value = "infantCount", required = false) Integer infantCount,
            @RequestParam("checkInDate") LocalDate checkInDate,
            @RequestParam("checkOutDate")  LocalDate checkOutDate,
            @RequestParam("longitude") Double longitude,
            @RequestParam("latitude") Double latitude,
            @RequestParam("lowestPrice") Integer lowestPrice,
            @RequestParam("highestPrice") Integer highestPrice
    )
    {
        return ResponseEntity.ok(accoService.getFilteredList(guestCount, infantCount, checkInDate, checkOutDate, longitude, latitude, lowestPrice, highestPrice));
    }

    @PostMapping("/reserve")
    public ResponseEntity<Void> reservation(@Valid @RequestBody AccoReservationRequest request,
                                            @RequestHeader("Authorization") String authHeader) {
        accoService.reservation(request, authHeader);

        return ResponseEntity.ok().build();
    }
}
