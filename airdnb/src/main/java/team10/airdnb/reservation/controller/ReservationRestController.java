package team10.airdnb.reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import team10.airdnb.reservation.controller.request.ReservationCreateRequest;
import team10.airdnb.reservation.controller.response.ReservationSummaryResponse;
import team10.airdnb.reservation.controller.response.ReservationInformationResponse;
import team10.airdnb.reservation.dto.ReservationAccommodationDto;
import team10.airdnb.reservation.service.ReservationService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReservationRestController {

    private final ReservationService reservationService;

    /* TODO
     * 전체 예약 목록 가져오기
     * 1) Host가 가지고 있는 하나의 숙소에 대한 모든 예약 불러오기
     * 2) Member가 예약한 모든 예약 내역 불러오기
     * -> 1, 2에 대해서는 Spring Security를 통한 role로 지정할 수 있지 않을까...?
     * */

    @GetMapping("/reservations")
    public ResponseEntity<?> getAllReservations() {
        log.info("예약 정보 전체 로드");

        return ResponseEntity.ok(reservationService.getReservations());
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<ReservationInformationResponse> getReservation(@PathVariable long reservationId) {
        ReservationInformationResponse response = reservationService.getReservation(reservationId);

        log.info("예약 정보 로드 : # {} : 숙소 이름 : {}, 예약자 이름 : {}",
                response.reservationId(),
                response.accommodationInformation().accommodationName(),
                response.memberInformation().memberName()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/reservation")
    public ResponseEntity<ReservationSummaryResponse> createReservation(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody @Valid ReservationCreateRequest request) {

        ReservationSummaryResponse response = reservationService.createReservation(request, authorizationHeader);


        log.info("예약 생성 완료 : # {} : 숙소 이름 : {}, 예약자 이름 : {}",
                response.reservationId(),
                response.accommodationInformation().accommodationName(),
                response.memberInformation().memberName()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<ReservationSummaryResponse> deleteReservation(@PathVariable Long reservationId) {
        ReservationSummaryResponse response = reservationService.deleteReservation(reservationId);

        log.info("예약 삭제 완료 : # {} : 숙소 이름 : {}, 예약자 이름 : {}",
                response.reservationId(),
                response.accommodationInformation().accommodationName(),
                response.memberInformation().memberName()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reservation/member")
    public ResponseEntity<List<ReservationAccommodationDto>> getReservationAccommodationDTOsByMemberId(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {

        List<ReservationAccommodationDto> reservations = reservationService.getReservationAccommodationDTOsByMemberId(authorizationHeader);
        return ResponseEntity.ok(reservations);
    }

}
