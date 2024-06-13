package team07.airbnb.controller;

import io.swagger.v3.oas.annotations.Operation;
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
import team07.airbnb.data.user.dto.response.TokenUserInfo;
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

    @Tag(name = "User")
    @Operation(summary = "예약 요금 정보 조회", description = "예약의 요금 정보를 조회합니다.")
    @GetMapping
    @ResponseStatus(OK)
    public PriceInfo getBookingPriceInfo(@RequestBody @Valid BookingRequest requestInfo) {
        return bookingService.getPriceInfo(BookingInfoForPriceInfo.ofRequest(requestInfo));
    }

    @Tag(name = "User")
    @Operation(summary = "예약 생성", description = "상품을 예약합니다.")
    @PostMapping
    @Authenticated(Role.USER)
    @ResponseStatus(CREATED)
    public BookingCreateResponse createBookingRequest(@RequestBody @Valid BookingRequest request, TokenUserInfo user) {
        return bookingService.createBooking(BookingInfoForPriceInfo.ofRequest(request), request.accommodationId(), userService.getCompleteUser(user));
    }

    @Tag(name = "Host")
    @Operation(summary = "예약 확정", description = "예약 상품의 호스트가 예약을 확정합니다.")
    @PostMapping("/confirm/{bookingId}")
    @Authenticated(Role.HOST)
    @ResponseStatus(OK)
    public Long confirmBooking(@PathVariable Long bookingId, TokenUserInfo user) {
        UserEntity host = userService.getCompleteUser(user);
        if (bookingService.isUserHostOf(bookingId, host)) {
            throw new UnAuthorizedException(BookingController.class, user.id());
        }

        //컨펌한 예약의 아이디 리턴
        return bookingService.confirmBooking(bookingId, host);
    }

    @Tag(name = "User")
    @Operation(summary = "예약 취소", description = "예약을 취소합니다.")
    @PostMapping("/cancel/{bookingId}")
    @Authenticated(Role.USER)
    @ResponseStatus(OK)
    public BookingCancelResponse cancelBooking(@PathVariable Long bookingId, TokenUserInfo user) {
        UserEntity booker = userService.getCompleteUser(user);

        if (bookingService.isUserBookerOf(bookingId, booker)) {
            throw new UnAuthorizedException(BookingController.class, user.id(), "해당 예약의 예약자가 아닙니다");
        }

        //취소 수수료 현재는 전체 결제 금액의 10%
        Integer cancelFee = bookingService.cancelBooking(bookingId, booker);

        return BookingCancelResponse.of(bookingId, cancelFee);
    }

    @Tag(name = "Host")
    @Operation(summary = "예약 이용 완료", description = "예약을 이용 완료 처리합니다.")
    @PostMapping("/complete/{bookingId}")
    @Authenticated(Role.HOST)
    public void completeBooking(@PathVariable Long bookingId, TokenUserInfo user){
         UserEntity host = userService.getCompleteUser(user);

        if (bookingService.isUserBookerOf(bookingId, host)) {
            throw new UnAuthorizedException(BookingController.class, user.id(), "해당 예약의 호스트가 아닙니다");
        }

        // 예약 종료 일자 전 예약 이용 완료 -> 남은 일자에 대해서 상품 재생성? 환불?
    }

    @Tag(name = "Host")
    @Operation(summary = "내 숙소의 예약 조회", description = "내가 등록한 숙소의 예약을 조회합니다.")
    @GetMapping("/hosting")
    @Authenticated(Role.HOST)
    @ResponseStatus(OK)
    public List<BookingManageInfoResponse> getBookingInfosOfHosting(TokenUserInfo host) {
        return bookingService.getBookingInfoListByHostId(userService.getCompleteUser(host));
    }

    @Tag(name = "User")
    @Operation(summary = "내 예약 조회", description = "내 예약을 조회합니다.")
    @GetMapping("my")
    @Authenticated(Role.USER)
    public List<BookingManageInfoResponse> getMyBookingInfos(TokenUserInfo user){
        return bookingService.getBookingInfoListByBookerId(userService.getCompleteUser(user));
    }

    @Operation(summary = "예약 상세 조회", description = "예약자나 호스트가 예약 상세 정보를 조회합니다.")
    @GetMapping("/{bookingId}")
    @Authenticated(Role.USER)
    @ResponseStatus(OK)
    public BookingDetailResponse getBookingDetail(@PathVariable Long bookingId, TokenUserInfo user) {
        if (bookingService.isUserHostOrBookerOf(bookingId, userService.getCompleteUser(user))

        ) {
            throw new UnAuthorizedException(BookingController.class, user.id());
        }
        return BookingDetailResponse.of(bookingService.findByBookingId(bookingId));
    }
}
