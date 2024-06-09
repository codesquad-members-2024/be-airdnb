package team07.airbnb.domain.booking;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.domain.auth.aop.Authenticated;
import team07.airbnb.domain.booking.dto.BookingInfo;
import team07.airbnb.domain.booking.dto.request.BookingRequest;
import team07.airbnb.domain.booking.dto.response.BookingCancelResponse;
import team07.airbnb.domain.booking.dto.response.BookingDetailResponse;
import team07.airbnb.domain.booking.dto.response.BookingManageInfoResponse;
import team07.airbnb.domain.booking.entity.BookingEntity;
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.user.enums.Role;
import java.util.List;

@Tag(name = "예약")
@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public BookingInfo getBookingInfo(@RequestBody @Valid BookingRequest requestInfo) {
        return bookingService.getBookingInfo(requestInfo);
    }

    @PostMapping
    @Authenticated(Role.USER)
    public ResponseEntity<Long> bookingRequest(@RequestBody @Valid BookingRequest request, UserEntity user) {
        BookingInfo bookingInfo = bookingService.getBookingInfo(request);
        if (bookingInfo.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        BookingEntity result = bookingService.createBooking(bookingInfo, request, user);

        return ResponseEntity.ok(result.getId());
    }

    @PostMapping("/confirm/{bookingId}")
    @Authenticated(Role.HOST)
    public ResponseEntity<Long> confirmBooking(@PathVariable Long bookingId, UserEntity host) {
        if (!bookingService.isRequestedHostSameInBooking(bookingId, host)) {
            throw new OAuth2AuthenticationException("해당 예약의 호스트가 아닙니다");
        }

        BookingEntity nowBooking = bookingService.findByBookingId(bookingId);

        BookingEntity confirmedBooking = bookingService.confirmBooking(nowBooking, host);

        return ResponseEntity.ok(confirmedBooking.getId());
    }

    @PostMapping("/cancel/{bookingId}")
    @Authenticated(Role.USER)
    public ResponseEntity<BookingCancelResponse> cancelBooking(@PathVariable Long bookingId, UserEntity booker) {
        if (!bookingService.isRequestedUserSameInBooking(bookingId, booker)) {
            throw new OAuth2AuthenticationException("해당 예약의 예약자가 아닙니다");
        }

        BookingEntity toCancel = bookingService.findByBookingId(bookingId);

        BookingEntity canceledBooking = bookingService.cancelBooking(toCancel, booker);

        long cancelFee = (long) (canceledBooking.getPayment().getTotalPrice() * (0.1));

        return ResponseEntity.ok(
                BookingCancelResponse.of(canceledBooking.getId(), cancelFee)
        );
    }

    @GetMapping("/management")
    @Authenticated(Role.HOST)
    public ResponseEntity<List<BookingManageInfoResponse>> getBookingInfoList(UserEntity host) {
        return ResponseEntity.ok(bookingService.getBookingInfoListByHostId(host));
    }

    @GetMapping("/management/{bookingId}")
    @Authenticated(Role.HOST)
    public ResponseEntity<BookingDetailResponse> getBookingDetail(@PathVariable Long bookingId, UserEntity host) {
        if (!bookingService.isRequestedHostSameInBooking(bookingId, host)) {
            throw new OAuth2AuthenticationException("해당 예약의 호스트가 아닙니다");
        }
        return ResponseEntity.ok(BookingDetailResponse.of(bookingService.findByBookingId(bookingId)));
    }
}
