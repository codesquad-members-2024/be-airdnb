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
import team07.airbnb.data.booking.dto.request.BookingPaymentsRequest;
import team07.airbnb.data.booking.dto.request.BookingRequest;
import team07.airbnb.data.booking.dto.request.CreateBookingRequest;
import team07.airbnb.data.booking.dto.response.BookingCancelResponse;
import team07.airbnb.data.booking.dto.response.BookingCreateResponse;
import team07.airbnb.data.booking.dto.response.BookingDetailResponse;
import team07.airbnb.data.booking.dto.transfer.BookingInfoForPriceInfo;
import team07.airbnb.data.booking.dto.transfer.DateInfo;
import team07.airbnb.data.booking.dto.transfer.DistanceInfo;
import team07.airbnb.data.booking.enums.CheckAuthType;
import team07.airbnb.data.user.dto.response.TokenUserInfo;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.exception.auth.UnAuthorizedException;
import team07.airbnb.service.booking.BookingAuthService;
import team07.airbnb.service.booking.BookingManageService;
import team07.airbnb.service.booking.BookingInquiryService;
import team07.airbnb.service.booking.BookingPriceService;
import team07.airbnb.service.user.UserService;

import java.util.Comparator;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static team07.airbnb.data.user.enums.Role.*;

@Tag(name = "예약")
@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private final BookingInquiryService bookingInquiryService;
    private final UserService userService;
    private final BookingPriceService bookingPriceService;
    private final BookingManageService bookingManageService;
    private final BookingAuthService bookingAuthService;

    @Tag(name = "User")
    @Operation(summary = "예약 요금 정보 조회", description = "예약의 요금 정보를 조회합니다.")
    @GetMapping
    @ResponseStatus(OK)
    public PriceInfo getBookingPriceInfo(@RequestBody @Valid BookingRequest requestInfo) {
        return bookingPriceService.getPriceInfo(
                requestInfo.accommodationId(),
                requestInfo.checkIn(),
                requestInfo.checkOut(),
                requestInfo.headCount()
        );
    }

    @Tag(name = "User")
    @Operation(summary = "예약 생성", description = "상품을 예약합니다.")
    @PostMapping
    @Authenticated(USER)
    @ResponseStatus(CREATED)
    public BookingCreateResponse createBookingRequest(@RequestBody @Valid CreateBookingRequest request, TokenUserInfo user) {
        return bookingManageService.createBooking(request, userService.getCompleteUser(user));
    }

    @Tag(name = "Host")
    @Operation(summary = "예약 확정", description = "예약 상품의 호스트가 예약을 확정합니다.")
    @PostMapping("/confirm/{bookingId}")
    @Authenticated(HOST)
    @ResponseStatus(OK)
    public Long confirmBooking(@PathVariable Long bookingId, TokenUserInfo user) {
        UserEntity host = bookingAuthService.currentUserIsSameWith(bookingId, user, CheckAuthType.HOST);

        //컨펌한 예약의 아이디 리턴
        return bookingManageService.confirmBooking(bookingId, host);
    }

    @Tag(name = "User")
    @Operation(summary = "예약 취소", description = "예약을 취소합니다.")
    @PostMapping("/cancel/{bookingId}")
    @Authenticated(USER)
    @ResponseStatus(OK)
    public BookingCancelResponse cancelBooking(@PathVariable Long bookingId, TokenUserInfo user) {
        UserEntity booker = bookingAuthService.currentUserIsSameWith(bookingId, user, CheckAuthType.USER);

        //취소 수수료 현재는 전체 결제 금액의 10%
        Integer cancelFee = bookingManageService.cancelBooking(bookingId, booker);

        return BookingCancelResponse.of(bookingId, cancelFee);
    }

    @Tag(name = "Host")
    @Operation(summary = "예약 이용 완료", description = "예약을 이용 완료 처리합니다.")
    @PostMapping("/complete/{bookingId}")
    @Authenticated(HOST)
    public void completeBooking(@PathVariable Long bookingId, TokenUserInfo user) {
        UserEntity host = bookingAuthService.currentUserIsSameWith(bookingId, user, CheckAuthType.HOST);

        // 예약 종료 일자 전 예약 이용 완료 -> 남은 일자에 대해서 상품 재생성, 환불 X
        bookingManageService.reopenBooking(bookingId);
    }

    @Tag(name = "User")
    @Operation(summary = "내 예약 조회", description = "내 예약을 조회합니다.")
    @GetMapping("/my-bookings")
    @Authenticated(USER)
    public List<BookingDetailResponse> getMyBookingInfos(TokenUserInfo user) {
        return bookingInquiryService.findBookingsByUser(userService.getCompleteUser(user));
    }

    @Operation(summary = "유저 예약 상세 조회", description = "유저가 예약 상세 정보를 조회합니다.")
    @GetMapping("/my-booking/{bookingId}")
    @Authenticated(USER)
    @ResponseStatus(OK)
    public BookingDetailResponse getDetailMyBooking(@PathVariable Long bookingId, TokenUserInfo userInfo) {
        bookingAuthService.currentUserIsSameWith(bookingId, userInfo, CheckAuthType.USER);

        return BookingDetailResponse.of(bookingInquiryService.findByBookingId(bookingId));
    }


    @Tag(name = "Host")
    @Operation(summary = "내 숙소의 예약 조회", description = "내가 등록한 숙소의 예약을 조회합니다.")
    @Authenticated(HOST)
    @GetMapping("/management")
    @ResponseStatus(OK)
    public List<BookingDetailResponse> getBookingInfosOfHosting(TokenUserInfo host) {
        return bookingInquiryService.getBookingInfoListByHost(userService.getCompleteUser(host));
    }


    @Operation(summary = "호스트 예약 상세 조회", description = "호스트가 예약 상세 정보를 조회합니다.")
    @GetMapping("/management/{bookingId}")
    @Authenticated(HOST)
    @ResponseStatus(OK)
    public BookingDetailResponse getBookingDetail(@PathVariable Long bookingId, TokenUserInfo host) {
        bookingAuthService.currentUserIsSameWith(bookingId, host, CheckAuthType.HOST);
        return BookingDetailResponse.of(bookingInquiryService.findByBookingId(bookingId));
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
