package team07.airbnb.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.common.auth.aop.Authenticated;
import team07.airbnb.data.booking.dto.PriceInfo;
import team07.airbnb.data.booking.dto.request.BookingRequest;
import team07.airbnb.data.booking.dto.response.BookingCancelResponse;
import team07.airbnb.data.booking.dto.response.BookingCreateResponse;
import team07.airbnb.data.booking.dto.response.BookingDetailResponse;
import team07.airbnb.data.booking.dto.response.BookingManageInfoResponse;
import team07.airbnb.data.booking.dto.transfer.BookingInfoForPriceInfo;
import team07.airbnb.data.user.dto.TokenUserInfo;
import team07.airbnb.data.user.enums.Role;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.exception.auth.UnAuthorizedException;
import team07.airbnb.service.booking.BookingService;
import team07.airbnb.service.user.UserService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Tag(name = "예약")
@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    @GetMapping
    @ResponseStatus(OK)
    public PriceInfo getBookingInfo(@RequestBody @Valid BookingRequest requestInfo) {
        return bookingService.getPriceInfo(BookingInfoForPriceInfo.ofRequest(requestInfo));
    }

    @PostMapping
    @Authenticated(Role.USER)
    @ResponseStatus(CREATED)
    public BookingCreateResponse createBookingRequest(@RequestBody @Valid BookingRequest request, TokenUserInfo user) {
        return bookingService.createBooking(BookingInfoForPriceInfo.ofRequest(request), request.accommodationId(), userService.getCompleteUser(user));
    }

    @PostMapping("/confirm/{bookingId}")
    @Authenticated(Role.HOST)
    @ResponseStatus(OK)
    public Long confirmBooking(@PathVariable Long bookingId, TokenUserInfo user) {
        UserEntity host = userService.getCompleteUser(user);
        if (bookingService.isRequestedHostNotMatchInBooking(bookingId, host)) {
            throw new UnAuthorizedException(BookingController.class, user.id());
        }

        //컨펌한 예약의 아이디 리턴
        return bookingService.confirmBooking(bookingId, host);
    }

    @PostMapping("/cancel/{bookingId}")
    @Authenticated(Role.USER)
    @ResponseStatus(OK)
    public BookingCancelResponse cancelBooking(@PathVariable Long bookingId, TokenUserInfo user) {
        UserEntity booker = userService.getCompleteUser(user);

        if (bookingService.isRequestedHostNotMatchInBooking(bookingId, booker)) {
            throw new UnAuthorizedException(BookingController.class, user.id(), "해당 예약의 예약자가 아닙니다");
        }

        //취소 수수료 현재는 전체 결제 금액의 10%
        Integer cancelFee = bookingService.cancelBooking(bookingId, booker);

        return BookingCancelResponse.of(bookingId, cancelFee);
    }

    @GetMapping("/management")
    @Authenticated(Role.HOST)
    @ResponseStatus(OK)
    public List<BookingManageInfoResponse> getBookingInfoList(TokenUserInfo host) {
        return bookingService.getBookingInfoListByHostId(userService.getCompleteUser(host));
    }

    @GetMapping("/management/{bookingId}")
    @Authenticated(Role.HOST)
    @ResponseStatus(OK)
    public BookingDetailResponse getBookingDetail(@PathVariable Long bookingId, TokenUserInfo host) {
        if (bookingService.isRequestedHostNotMatchInBooking(bookingId, userService.getCompleteUser(host))) {
            throw new UnAuthorizedException(BookingController.class, host.id());
        }
        return BookingDetailResponse.of(bookingService.findByBookingId(bookingId));
    }
}
