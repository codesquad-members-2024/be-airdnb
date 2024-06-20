package team07.airbnb.controller.booking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.data.booking.dto.PriceInfo;
import team07.airbnb.data.booking.dto.request.BookingPaymentsRequest;
import team07.airbnb.data.booking.dto.request.BookingRequest;
import team07.airbnb.data.booking.dto.transfer.DateInfo;
import team07.airbnb.data.booking.dto.transfer.DistanceInfo;
import team07.airbnb.service.booking.BookingInquiryService;
import team07.airbnb.service.booking.BookingPriceService;

import java.util.Comparator;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class BookingGuestController {

    private final BookingPriceService bookingPriceService;
    private final BookingInquiryService bookingInquiryService;

    @Tag(name = "User")
    @Operation(summary = "예약 요금 정보 조회", description = "예약의 요금 정보를 조회합니다.")
    @GetMapping
    @ResponseStatus(OK)
    public PriceInfo getBookingPriceInfo(@ModelAttribute("requestInfo") @Valid BookingRequest requestInfo) {
        return bookingPriceService.getPriceInfo(
                requestInfo.accommodationId(),
                requestInfo.checkIn(),
                requestInfo.checkOut(),
                requestInfo.headCount()
        );
    }

    @Operation(summary = "날짜와 지역에 따른 요금정보들 조회", description = "요금 그래프를 위한 선택한 지역, 날짜의 평균 요금들을 정렬된 리스트로 반환합니다.")
    @GetMapping("/pay-info")
    @ResponseStatus(OK)
    public List<Double> getPayInfo(@RequestBody BookingPaymentsRequest bookingPaymentsRequest) {
        List<Double> result = bookingInquiryService.getPricesAccAvailable(
                DateInfo.of(bookingPaymentsRequest),
                DistanceInfo.of(bookingPaymentsRequest)
        );
        result.sort(Comparator.naturalOrder());
        return result;
    }
}
