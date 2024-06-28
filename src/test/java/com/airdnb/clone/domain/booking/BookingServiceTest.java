package com.airdnb.clone.domain.booking;

import com.airdnb.clone.domain.booking.controller.request.BookingSaveRequest;
import com.airdnb.clone.domain.booking.repository.BookingRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookingServiceTest {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingRepository bookingRepository;

    @Test
    void 동시_예약_요청이_오더라도_1건만_예약된다() throws InterruptedException {
        LocalDateTime checkIn = LocalDateTime.of(LocalDate.of(2024, 8, 1), LocalTime.of(15, 0, 0));
        LocalDateTime checkOut = LocalDateTime.of(LocalDate.of(2024, 8, 2), LocalTime.of(12, 0, 0));
        BookingSaveRequest request = new BookingSaveRequest(1L, 1L, checkIn, checkOut, 2);

        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        long originCount = bookingRepository.count();
        int requestCount = 30;

        ExecutorService executorService = Executors.newFixedThreadPool(30);
        CountDownLatch latch = new CountDownLatch(requestCount);

        for (int i = 0; i < requestCount; i++) {
            executorService.submit(() -> {
                try {
                    bookingService.create(request);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        System.out.println("successCount = " + successCount);
        System.out.println("failCount = " + failCount);

        long changeCount = bookingRepository.count();
        Assertions.assertThat(originCount + 1).isEqualTo(changeCount);
    }
}