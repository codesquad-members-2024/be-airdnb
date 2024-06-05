package team07.airbnb.domain.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.domain.booking.dto.request.BookingRequestInfo;
import team07.airbnb.domain.booking.dto.response.BookingInfo;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public BookingInfo getBookingInfo(@RequestBody BookingRequestInfo requestInfo) {
        return bookingService.getBookingInfo(requestInfo);
    }
}
