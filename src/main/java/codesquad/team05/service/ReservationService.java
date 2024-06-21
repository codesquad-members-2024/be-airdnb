package codesquad.team05.service;

import codesquad.team05.domain.accomodation.Accommodation;
import codesquad.team05.domain.accomodation.AccommodationRepository;
import codesquad.team05.domain.accomodation.AccommodationType;
import codesquad.team05.domain.coupon.UserCoupon;
import codesquad.team05.domain.coupon.UserCouponRepository;
import codesquad.team05.domain.discount.DiscountPolicy;
import codesquad.team05.domain.discount.DiscountPolicyRepository;
import codesquad.team05.domain.reservation.Reservation;
import codesquad.team05.domain.reservation.ReservationRepository;
import codesquad.team05.domain.servicecharge.ServiceCharge;
import codesquad.team05.domain.servicecharge.ServiceChargeRepository;
import codesquad.team05.domain.user.User;
import codesquad.team05.domain.user.UserRepository;
import codesquad.team05.exception.*;
import codesquad.team05.web.reservation.dto.request.ReservationServiceDto;
import codesquad.team05.web.reservation.dto.request.ReservationUpdate;
import codesquad.team05.web.reservation.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.IntStream;

import static codesquad.team05.domain.servicecharge.Charge.CHARGE_FOR_GUEST;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final ServiceChargeRepository serviceChargeRepository;
    private final UserCouponRepository userCouponRepository;
    private final DiscountPolicyRepository discountPolicyRepository;


    public Long createReservation(Long userId, Long accommodationId, ReservationServiceDto serviceDto) {
        Accommodation accommodation = accommodationRepository
                .findById(accommodationId)
                .orElseThrow();

        int amount = calculateTotalAmount(serviceDto.getCheckIn()
                , serviceDto.getCheckOut()
                , accommodation
                , userId
                , serviceDto.getCouponsToUse());

        Reservation reservation = serviceDto.toEntityForSave(amount);
        User user = userRepository.findById(userId).orElseThrow();

        validateReservation(serviceDto, accommodation);

        reservation.setUser(user);
        reservation.setAccommodation(accommodation);

        return reservationRepository.save(reservation).getId();
    }

    public ReservationResponse getReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow();
        return reservation.toEntity();

    }

    public void updateReservation(Long reservationId, ReservationUpdate reservationUpdate) {

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        reservation.update(reservationUpdate);

    }

    private int calculateTotalAmount(LocalDate checkIn
            , LocalDate checkOut
            , Accommodation accommodation
            , Long userId
            , Long couponsToUse) {

        /*
         의도치 않는 중복 할인을 막기 위해서, 호스트가 설정한 할인 금액과 주 단위 4% 할인을 별도로 처리했음.

         ex) 숙박 요금(10만원)에서 호스트가 10% 할인을 설정함 --> 1만원 할인
             7일 숙박 기간에 따른 주 단위 할인(4%) --> 10만원 * 0.04 --> 4천원 할인

             총 결제 금액 = 10만원 -(1만원+4천원) --> 8만 6천원
         */

        int days = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        int weeks = days / 7;
        int remainingDays = days % 7;
        int accommodationPrice = accommodation.getPrice();
        int couponDiscountAmount = 0;
        double totalAmount = 0;

        double amountByDays = IntStream
                .range(0, weeks)
                .mapToDouble(i -> 7 * accommodationPrice * 0.96)
                .sum();

        totalAmount += amountByDays + remainingDays * accommodationPrice;

        // 게스트는 air_dnb에 숙박 요금의 14.2%를 수수료로 낸다.

        int chargeForPlatform = (int) (totalAmount * CHARGE_FOR_GUEST);

        if (accommodation.isOnSale()) {
            totalAmount -= calculateDiscountAmount(accommodation);
        }

        if (couponsToUse != null) {
            couponDiscountAmount =
                    calculateCouponAmount(userId
                            , couponsToUse
                            , totalAmount
                            , accommodation.getAccommodationType());
        }

        // 호스트가 설정해 둔 서비스 비용에 대한 요금 추가

        List<ServiceCharge> serviceCharges =
                serviceChargeRepository
                        .findByAccommodationId(accommodation.getId());

        if (!serviceCharges.isEmpty()) {
            totalAmount += plusServiceCharge(serviceCharges, days);
        }

        return (int) totalAmount + chargeForPlatform - couponDiscountAmount;
    }

    private int calculateCouponAmount(Long userId
            , Long couponToUse
            , double amount
            , AccommodationType accommodationType) {

        List<UserCoupon> couponList = userCouponRepository.findByCouponId(couponToUse);

        UserCoupon coupon = couponList.get(0);

        validateCoupon(userId, amount, accommodationType, couponList, coupon);

        double couponAmount = coupon.calculateCouponAmount(amount);

        /*
         쿠폰으로 할인 받는 금액과 쿠폰에서 설정한 최대 할인 가능 금액을 비교한다.
         쿠폰 할인 금액 > 쿠폰의 최대 할인 가능 금액인 경우, 최대 할인 가능 금액 만큼만 요금을 할인해 준다.
         */

        int maximumDiscountAmount = coupon.getMaximumDiscountAmount();

        if (maximumDiscountAmount < couponAmount) {
            return maximumDiscountAmount;
        }

        return (int) couponAmount;

    }

    private void validateReservation(ReservationServiceDto serviceDto, Accommodation accommodation) {
        if (serviceDto.getPersonCount() > accommodation.getMaxCapacity()) {
            throw new PersonCountExceededException("허용된 인원 수를 초과했습니다.");
        }

        if (!isReservationAvailable(serviceDto.getCheckIn(), serviceDto.getCheckOut(), accommodation)) {
            throw new DateAlreadyBookedException("이미 예약된 상품입니다.");
        }
    }


    private boolean isReservationAvailable(LocalDate checkIn
            , LocalDate checkOut
            , Accommodation accommodation) {

        /*
         이용자가 신청한 예약 기간이 해당 숙소에 걸려 있던 예약 기간과 겹치는 지 확인한다.

         ex) 이용자가 신청한 예약 기간 6월 23일 ~ 6월 26일
             숙소에 걸려 있던 예약 (1) 6월 19일 ~ 22일, (2) 6월 27일 ~ 29일


            (1)번의 경우, 새로운 예약의 체크인 시간이 (1)번의 체크아웃 시간(22일)보다 뒤라서 예약 기간이 겹치지 않는다.

            (2)번의 경우, 새로운 예약의 체크아웃 시간이 (2)번의 체크인 시간(27일)보다 앞이라서 예약 기간이 겹치지 않는다.
         */

        List<Reservation> list = reservationRepository.findReservationByAccommodationId(accommodation.getId());

        return list.stream().allMatch((i) -> {
            LocalDate reservationCheckIn = i.getCheckIn();
            LocalDate reservationCheckOut = i.getCheckOut();

            return checkOut.isBefore(reservationCheckIn) || checkIn.isAfter(reservationCheckOut);

        });
    }


    private int calculateDiscountAmount(Accommodation accommodation) {
        DiscountPolicy discountPolicy = discountPolicyRepository.findByAccommodationId(accommodation.getId());


        return (int) (accommodation.getPrice() * discountPolicy.getDiscountRate());
    }

    private int plusServiceCharge(List<ServiceCharge> chargeList, int days) {
        return chargeList.stream().mapToInt(ch -> ch.calculateFee(days)).sum();

    }


    private void validateCoupon(Long userId, double amount, AccommodationType accommodationType, List<UserCoupon> coupons, UserCoupon userCoupon) {

        /*
         쿠폰 할인 시 거치는 검증 단계

         1)이용자가 사용하려는 쿠폰을 실제로 소유하고 있는지 확인한다.

         2)쿠폰에 명시된 숙소의 타입과 예약하려는 숙소의 타입이 일치하는지 확인한다.

         3)사용하려는 쿠폰의 사용 가능 기간이 유효한지 확인한다.

         4)숙소 예약 금액이 쿠폰에 명시된 최소 주문 금액을 넘었는지 확인한다.
         */

        if (!validateUser(userId, coupons)) {
            throw new CouponNotOwnedException("해당 쿠폰을 소유하고 있지 않습니다.");
        }

        if (!userCoupon.validateAccommodationType(accommodationType)) {
            throw new AccommodationTypeMismatchException("숙소의 타입이 일치하지 않습니다.");
        }

        if (!userCoupon.validateExpirationDate(LocalDate.now())) {
            throw new CouponExpiredException("쿠폰의 유효기간이 지났습니다.");
        }

        if (!userCoupon.isAboveMinimumAmount(amount)) {
            throw new MinimumPaymentAmountNotMetException("할인 쿠폰의 최소 주문금액을 넘지 않았습니다.");
        }
    }

    private boolean validateUser(Long userId, List<UserCoupon> coupons) {
        return coupons.stream().anyMatch(coupon -> coupon.isOwnedBy(userId));
    }
}
