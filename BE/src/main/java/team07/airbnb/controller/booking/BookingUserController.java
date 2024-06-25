package team07.airbnb.controller.booking;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import team07.airbnb.data.booking.dto.transfer.DateInfo;
import team07.airbnb.data.booking.dto.transfer.DistanceInfo;
import team07.airbnb.data.booking.enums.CheckAuthType;
import team07.airbnb.data.user.dto.response.TokenUserInfo;
import team07.airbnb.entity.UserEntity;
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
public class BookingUserController {

    private final BookingInquiryService bookingInquiryService;
    private final UserService userService;
    private final BookingManageService bookingManageService;
    private final BookingAuthService bookingAuthService;


    @Tag(name = "User")
    @Operation(summary = "예약 생성", description = "상품을 예약합니다.")
    @PostMapping
    @Authenticated(USER)
    @ResponseStatus(CREATED)
    public BookingCreateResponse createBookingRequest(@RequestBody @Valid CreateBookingRequest request,
                                                      @Parameter(hidden = true) UserEntity user) {
        log.info("예약 생성 들어옴");
        BookingCreateResponse response = bookingManageService.createBooking(request, user);
        log.info("예약 생성됨. id = {%d}".formatted(response.bookingId()));
        return response;
    }

    @Tag(name = "User")
    @Operation(summary = "예약 취소", description = "예약을 취소합니다.")
    @PostMapping("/cancel/{bookingId}")
    @Authenticated(USER)
    @ResponseStatus(OK)
    public BookingCancelResponse cancelBooking(@PathVariable Long bookingId,
                                               @Parameter(hidden = true) UserEntity user) {
        bookingAuthService.currentUserIsSameWith(bookingId, user, CheckAuthType.USER);

        //취소 수수료 현재는 전체 결제 금액의 10%
        Integer cancelFee = bookingManageService.cancelBooking(bookingId, user);

        return BookingCancelResponse.of(bookingId, cancelFee);
    }


    @Tag(name = "User")
    @Operation(summary = "내 예약 조회", description = "내 예약을 조회합니다.")
    @GetMapping("/my-bookings")
    @Authenticated(USER)
    public List<BookingDetailResponse> getMyBookingInfos(@Parameter(hidden = true) UserEntity user) {
        return bookingInquiryService.findBookingsByUser(user);
    }

    @Operation(summary = "유저 예약 상세 조회", description = "유저가 예약 상세 정보를 조회합니다.")
    @GetMapping("/my-booking/{bookingId}")
    @Authenticated(USER)
    @ResponseStatus(OK)
    public BookingDetailResponse getDetailMyBooking(@PathVariable Long bookingId,
                                                    @Parameter(hidden = true) UserEntity user) {
        bookingAuthService.currentUserIsSameWith(bookingId, user, CheckAuthType.USER);

        return BookingDetailResponse.of(bookingInquiryService.findByBookingId(bookingId));
    }
}
