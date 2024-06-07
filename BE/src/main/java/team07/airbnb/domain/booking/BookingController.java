package team07.airbnb.domain.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.domain.auth.aop.Authenticated;
import team07.airbnb.domain.booking.dto.request.BookingRequest;
import team07.airbnb.domain.booking.dto.response.BookingInfo;
import team07.airbnb.domain.booking.entity.BookingEntity;
import team07.airbnb.domain.payment.PaymentEntity;
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.user.enums.Role;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public BookingInfo getBookingInfo(@RequestBody BookingRequest requestInfo) {
        return bookingService.getBookingInfo(requestInfo);
    }

    @PostMapping
    @Authenticated(Role.USER)
    public ResponseEntity<Void> bookingRequest(@RequestBody BookingRequest request, UserEntity user) {
        BookingInfo bookingInfo = bookingService.getBookingInfo(request);
        if (bookingInfo.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        bookingService.createBooking(bookingInfo, request, user);

        return ResponseEntity.ok().build();
    }
}
