package codesquad.airdnb.domain.accommodation.controller;

import codesquad.airdnb.domain.accommodation.dto.request.AccoReservationRequest;
import codesquad.airdnb.domain.accommodation.service.AccoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/accommodations")
@RequiredArgsConstructor
public class GuestAccoController {

    private final AccoService accoService;

    @GetMapping("/{accoId}")
    public ResponseEntity<?> getAccoByCondition(
            @RequestParam("guestCount") Integer guestCount,
            @RequestParam(value = "infantCount", required = false) Integer infantCount,
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam("longitude") Double longitude,
            @RequestParam("latitude") Double latitude
    )
    {
        return ResponseEntity.ok(accoService.getFilteredList(guestCount, infantCount, checkInDate, checkOutDate, longitude, latitude));
    }

    // TODO: 이후 로그인이 구현되면 토큰 등으로 로그인한 사용자의 정보를 전달받도록 변경
    @PostMapping("/reserve")
    public ResponseEntity<Void> reservation(@Valid @RequestBody AccoReservationRequest request,
                                            @RequestParam Long memberId) {
        accoService.reservation(request, memberId);

        return ResponseEntity.ok().build();
    }
}
