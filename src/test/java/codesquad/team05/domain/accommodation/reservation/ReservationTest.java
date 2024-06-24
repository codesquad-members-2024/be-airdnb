package codesquad.team05.domain.accommodation.reservation;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.accommodation.AccommodationRepository;
import codesquad.team05.domain.reservation.Reservation;
import codesquad.team05.domain.reservation.ReservationRepository;
import codesquad.team05.domain.review.Review;
import codesquad.team05.domain.review.ReviewRepository;
import codesquad.team05.domain.user.User;
import codesquad.team05.domain.user.UserRepository;
import codesquad.team05.exception.DateAlreadyBookedException;
import codesquad.team05.exception.PersonCountExceededException;
import codesquad.team05.service.AccommodationService;
import codesquad.team05.service.ReservationService;
import codesquad.team05.service.ServiceChargeService;
import codesquad.team05.util.AccommodationMapper;
import codesquad.team05.web.accommodation.dto.request.AccommodationUpdate;
import codesquad.team05.web.reservation.dto.request.ReservationServiceDto;
import codesquad.team05.web.reservation.dto.request.ReservationUpdate;
import codesquad.team05.web.reservation.dto.response.ReservationResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static codesquad.team05.domain.accommodation.AccommodationType.HOTEL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@Transactional
class ReservationTest {

    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final AccommodationService accommodationService;
    private final ReservationService reservationService;

    @Autowired
    public ReservationTest(UserRepository userRepository, AccommodationRepository accommodationRepository, ReservationRepository reservationRepository, ReviewRepository reviewRepository, AccommodationService accommodationService, ReservationService reservationService, ServiceChargeService serviceChargeService) {
        this.userRepository = userRepository;
        this.accommodationRepository = accommodationRepository;
        this.reservationRepository = reservationRepository;
        this.reviewRepository = reviewRepository;
        this.accommodationService = accommodationService;
        this.reservationService = reservationService;
    }

    @DisplayName("숙박 시설을 예약한다.")
    @Test
    public void test() {
        User user1 = new User("fnelclsrn", "정연호", "123", "thtk", LocalDate.now());
        User user2 = new User("fnelclsrn1", "정연호", "123", "thtk", LocalDate.now());

        userRepository.save(user1);
        userRepository.save(user2);

        user1.enrollHost();

        ReservationServiceDto reservationServiceDto = new ReservationServiceDto();
        reservationServiceDto.setCheckIn(LocalDate.now());
        reservationServiceDto.setCheckOut(LocalDate.now().plusDays(2));
        reservationServiceDto.setPersonCount(3);
        reservationServiceDto.setRegisteredAt(LocalDateTime.now());

        Accommodation accommodation = new Accommodation(
                "숙소2",
                1000,
                "소사",
                3,
                1,
                1,
                "원룸",
                "없음",
                HOTEL
        );
        Accommodation saved = accommodationRepository.save(accommodation);

        Long reservationId = reservationService.createReservation(2L, saved.getId(), reservationServiceDto);
        ReservationResponse reservation = reservationService.getReservation(reservationId);

        int amount = (int) (2000 + 2000 * 0.142);

        SoftAssertions a = new SoftAssertions();
        a.assertThat(reservation.getAmount()).isEqualTo(amount);
        a.assertThat(reservation.getPersonCount()).isEqualTo(3);
        a.assertThat(reservation.getUser().getId()).isEqualTo(1L);
    }


    //숙박 시설 할인 정책 검사
    @DisplayName("호스트가 숙소에 대한 할인 정책을 설정한다.")
    @Test
    public void test23() {
        User user1 = new User("fnelclsrn", "정연호", "123", "thtk", LocalDate.now());
        userRepository.save(user1);
        user1.enrollHost();

        Accommodation accommodation = new Accommodation(
                "숙소2",
                1000,
                "소사",
                3,
                1,
                1,
                "원룸",
                "없음",
                HOTEL
        );
        Accommodation saved = accommodationRepository.save(accommodation);
    }

    //숙박 시설 서비스 요금 검사
    //숙박 시설에 대한 제대로 된 비용 검사
    @Test
    public void test2() {
        User user = new User("alexa", "jeon22", "1234", "집주소", LocalDate.now());
        userRepository.save(user);
        Review reviews = new Review();
        reviews.setComment("숙소가 좋아요");
        reviews.setRating(3);

        Accommodation accommodation = new Accommodation(
                "숙소",
                1000,
                "소사",
                1,
                1,
                1,
                "원룸",
                "없음",
                HOTEL
        );
        accommodationRepository.save(accommodation);
        reviews.setAccommodation(accommodation);
        reviews.setUser(user);
        reviewRepository.save(reviews);
    }

    @Test
    public void test3() {
        accommodationService.updateAccommodation(1L, AccommodationMapper.toUpdateService(
                new AccommodationUpdate(
                        "숙소변경",
                        10000,
                        "소사",
                        1,
                        1,
                        1,
                        "원룸",
                        "없음",
                        null
                ), new ArrayList<>()));

        assertThat(accommodationService.getAccommodationById(1L).getName()).isEqualTo("숙소변경");
    }

    @Test
    @Commit
    public void setUpData() {
        User user = new User("alexaa", "jeon22", "1234", "집주소", LocalDate.now());
        userRepository.save(user);

        Accommodation accommodation = new Accommodation(
                "숙소2",
                1000,
                "소사",
                1,
                1,
                1,
                "원룸",
                "없음",
                HOTEL
        );
        accommodationRepository.save(accommodation);

        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setAccommodation(accommodation);
        reservation.setCheckIn(LocalDate.now());
        reservation.setCheckOut(LocalDate.now().plusDays(1));
        reservation.setAmount(100);
        reservation.setPersonCount(2);
        reservation.setRegisteredAt(LocalDateTime.now());
        reservationRepository.save(reservation);
    }


    @Test

    public void test4() throws InterruptedException {
        setUpData();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Future<?> future = executorService.submit(
                () -> reservationService.updateReservation(1L, new ReservationUpdate(3, 3, LocalDate.now(), LocalDate.now().plusDays(1))));
        Future<?> future2 = executorService.submit(
                () -> reservationService.updateReservation(1L, new ReservationUpdate(3, 3, LocalDate.now(), LocalDate.now().plusDays(1))));
        Future<?> future3 = executorService.submit(
                () -> reservationService.updateReservation(1L, new ReservationUpdate(3, 3, LocalDate.now(), LocalDate.now().plusDays(1))));

        Exception result = new Exception();

        try {
            future.get();
            future2.get();
            future3.get();
        } catch (ExecutionException e) {
            result = (Exception) e.getCause();
        }

        assertTrue(result instanceof OptimisticLockingFailureException);
    }


    @DisplayName("이미 예약된 날짜에 예약을 신청하면 예약에 실패한다.")
    @Test
    public void test5() {
        ReservationServiceDto reservationServiceDto = new ReservationServiceDto();
        reservationServiceDto.setCheckIn(LocalDate.now());
        reservationServiceDto.setCheckOut(LocalDate.now().plusDays(1));
        reservationServiceDto.setPersonCount(0);
        reservationServiceDto.setRegisteredAt(LocalDateTime.now());


        Accommodation accommodation = new Accommodation("숙소2", 1000, "소사", 1, 1, 1, "원룸", "없음", HOTEL);
        Accommodation saved = accommodationRepository.save(accommodation);

        reservationService.createReservation(1L, saved.getId(), reservationServiceDto);


        assertThatThrownBy(() -> reservationService.createReservation(1L, saved.getId(), reservationServiceDto))
                .isInstanceOf(DateAlreadyBookedException.class);

    }

    @DisplayName("숙소 인원수보다 더 많은 사람으로 에약하면 예약에 실패한다.")
    @Test
    public void test6() {
        ReservationServiceDto reservationServiceDto = new ReservationServiceDto();
        reservationServiceDto.setCheckIn(LocalDate.now());
        reservationServiceDto.setCheckOut(LocalDate.now().plusDays(1));
        reservationServiceDto.setPersonCount(3);
        reservationServiceDto.setRegisteredAt(LocalDateTime.now());

        Accommodation accommodation = new Accommodation("숙소2", 1000, "소사", 1, 1, 1, "원룸", "없음", HOTEL);
        Accommodation saved = accommodationRepository.save(accommodation);


        assertThatThrownBy(() -> reservationService.createReservation(1L, 1L, reservationServiceDto))
                .isInstanceOf(PersonCountExceededException.class);
    }
}