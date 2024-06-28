package com.team01.airdnb.reservation;

import com.team01.airdnb.accommadation.AccommodationRepository;
import com.team01.airdnb.reservation.dto.ReservationRequest;
import com.team01.airdnb.user.User;
import com.team01.airdnb.user.UserRepository;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@Import({TestConfig.class})
//@DataJpaTest
@SpringBootTest
class ReservationServiceTest {

  @Autowired
  AccommodationRepository accommodationRepository;
  @Autowired
  ReservationService reservationService;
  @Autowired
  UserRepository userRepository;

  @Test
  public void 동시_예약_테스트() throws InterruptedException {
    LocalDate checkin = LocalDate.of(2024, 10, 1);
    LocalDate checkout = LocalDate.of(2024, 10, 5);

    int numberOfThreads = 2000;
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
    CountDownLatch latch = new CountDownLatch(numberOfThreads);

    for (int i = 0; i < numberOfThreads; i++) {
      final Long userId = (long) (i);

      executorService.execute(() -> {
        try {
          createReservationForUser(userId, checkin, checkout);
        } catch (Exception e) {
          System.out.println("예약 실패한 유저: " + userId + ", 사유: " + e.getMessage());
        } finally {
          latch.countDown();
        }
      });
    }
    latch.await();
    executorService.shutdown();
  }

  public void createReservationForUser(Long userId, LocalDate checkin, LocalDate checkout) {
    User user = new User();
    user = userRepository.save(user);

    ReservationRequest reservationRequest = new ReservationRequest(
        1, 1, 1, 0, 1000L, checkin, checkout, user.getId(), 3L);

    System.out.println("예약을 시도중인 유저: " + user.getId());
    reservationService.createReservation(reservationRequest);
    System.out.println("=====예약을 성공한 유저=====: " + user.getId());
  }
}