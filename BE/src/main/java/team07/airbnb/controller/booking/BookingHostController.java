package team07.airbnb.controller.booking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.common.auth.aop.Authenticated;
import team07.airbnb.data.booking.dto.response.BookingDetailResponse;
import team07.airbnb.data.booking.enums.CheckAuthType;
import team07.airbnb.data.user.dto.response.TokenUserInfo;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.service.booking.BookingAuthService;
import team07.airbnb.service.booking.BookingInquiryService;
import team07.airbnb.service.booking.BookingManageService;
import team07.airbnb.service.user.UserService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static team07.airbnb.data.user.enums.Role.HOST;

@RestController
@RequiredArgsConstructor
public class BookingHostController {

    private final BookingAuthService bookingAuthService;
    private final BookingManageService bookingManageService;
    private final BookingInquiryService bookingInquiryService;
    private final UserService userService;

    @Tag(name = "Host")
    @Operation(summary = "예약 확정", description = "예약 상품의 호스트가 예약을 확정합니다.")
    @PostMapping("/confirm/{bookingId}")
    @Authenticated(HOST)
    @ResponseStatus(OK)
    public Long confirmBooking(@PathVariable Long bookingId, UserEntity user) {
        bookingAuthService.currentUserIsSameWith(bookingId, user, CheckAuthType.HOST);

        //컨펌한 예약의 아이디 리턴
        return bookingManageService.confirmBooking(bookingId, user);
    }

    @Tag(name = "Host")
    @Operation(summary = "예약 이용 완료", description = "예약을 이용 완료 처리합니다.")
    @PostMapping("/complete/{bookingId}")
    @Authenticated(HOST)
    public void completeBooking(@PathVariable Long bookingId, UserEntity user) {
        bookingAuthService.currentUserIsSameWith(bookingId, user, CheckAuthType.HOST);

        // 예약 종료 일자 전 예약 이용 완료 -> 남은 일자에 대해서 상품 재생성, 환불 X
        bookingManageService.reopenBooking(bookingId);
    }

    @Tag(name = "Host")
    @Operation(summary = "내 숙소의 예약 조회", description = "내가 등록한 숙소의 예약을 조회합니다.")
    @Authenticated(HOST)
    @GetMapping("/management")
    @ResponseStatus(OK)
    public List<BookingDetailResponse> getBookingInfosOfHosting(UserEntity host) {
        return bookingInquiryService.getBookingInfoListByHost(host);
    }


    @Operation(summary = "호스트 예약 상세 조회", description = "호스트가 예약 상세 정보를 조회합니다.")
    @GetMapping("/management/{bookingId}")
    @Authenticated(HOST)
    @ResponseStatus(OK)
    public BookingDetailResponse getBookingDetail(@PathVariable Long bookingId, UserEntity host) {
        bookingAuthService.currentUserIsSameWith(bookingId, host, CheckAuthType.HOST);
        return BookingDetailResponse.of(bookingInquiryService.findByBookingId(bookingId));
    }
}
