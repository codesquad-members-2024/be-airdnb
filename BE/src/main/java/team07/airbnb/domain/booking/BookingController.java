package team07.airbnb.domain.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.domain.auth.aop.Authenticated;
import team07.airbnb.domain.booking.dto.request.BookingRequestInfo;
import team07.airbnb.domain.booking.dto.response.BookingInfo;
import team07.airbnb.domain.user.entity.UserEntity;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    @Authenticated
    public BookingInfo getBookingInfo(@RequestBody BookingRequestInfo requestInfo, UserEntity user) {
        log.info(user.getName());
        return bookingService.getBookingInfo(requestInfo);
    }
}
