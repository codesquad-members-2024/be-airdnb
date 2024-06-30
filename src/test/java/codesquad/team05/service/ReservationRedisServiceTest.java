package codesquad.team05.service;

import codesquad.team05.domain.reservation.Reservation;
import codesquad.team05.domain.reservation.ReservationRepository;
import codesquad.team05.util.AccommodationMapper;
import codesquad.team05.web.accommodation.dto.request.AccommodationSave;
import codesquad.team05.web.reservation.dto.request.ReservationServiceDto;
import codesquad.team05.web.user.dto.request.JoinRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static codesquad.team05.domain.accommodation.AccommodationType.HOTEL;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ReservationRedisServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ReservationRedisServiceTest.class);
    private ReservationRedisService reservationRedisService;
    private UserService userService;
    private AccommodationService accommodationService;
    private ReservationRepository reservationRepository;

    @Autowired
    public ReservationRedisServiceTest(ReservationRedisService reservationRedisService, UserService userService, AccommodationService accommodationService, ReservationRepository reservationRepository) {
        this.reservationRedisService = reservationRedisService;
        this.userService = userService;
        this.accommodationService = accommodationService;
        this.reservationRepository = reservationRepository;
    }

    @Test
    public void test1() throws InterruptedException {
        final AtomicLong firstSuccessfulUserId = new AtomicLong(-1);
        int threadCount = 1000;

        JoinRequest joinRequest = new JoinRequest("dusgh", "정연호", "123", "소사", LocalDate.now());

        userService.save(joinRequest.toServiceDto());


        AccommodationSave accommodationSave = new AccommodationSave(
                "숙소1",
                1000,
                "소사1",
                1,
                2,
                3,
                "집 좋음",
                "없음",
                HOTEL,
                null
        );

        Long id = accommodationService.register(AccommodationMapper.toSaveService(accommodationSave, new ArrayList<>()));

        ExecutorService executorService = Executors.newFixedThreadPool(32);

        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        ReservationServiceDto reservationServiceDto = new ReservationServiceDto();
        reservationServiceDto.setCheckIn(LocalDate.now());
        reservationServiceDto.setCheckOut(LocalDate.now().plusDays(2));
        reservationServiceDto.setPersonCount(1);
        reservationServiceDto.setRegisteredAt(LocalDateTime.now());

        for (Long i = 1L; i < threadCount; i++) {

            Long userId = i;

            executorService.submit(() -> {
                try {
                    log.info(Thread.currentThread().getName());
                    boolean success = reservationRedisService.createReservation(userId, id, reservationServiceDto);
                    if(success) {
                        firstSuccessfulUserId.compareAndSet(-1, userId);
                        System.out.println("First successful reservation made by user ID: " + firstSuccessfulUserId.get());

                    }
                } catch (IllegalStateException e) {
                    System.out.println(userId + " 스레드 실패");
                }

                finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await(20, TimeUnit.SECONDS);

        Thread.sleep(10000);
        executorService.shutdown();

        // 첫 번째 성공한 예약이 있는지 검증
        assertTrue(firstSuccessfulUserId.get() != -1);
        assertTrue(reservationRepository.count()==1L);
        List<Reservation> byAccommodationId = reservationRepository.findReservationByAccommodationId(id);
        assertTrue(byAccommodationId.size()==1);
        assertTrue(byAccommodationId.get(0).getUser().getId() ==firstSuccessfulUserId.get());
        assertTrue(byAccommodationId.get(0).getUser().getId() ==1L);
        System.out.println("First successful reservation made by user ID: " + firstSuccessfulUserId.get());


    }

}