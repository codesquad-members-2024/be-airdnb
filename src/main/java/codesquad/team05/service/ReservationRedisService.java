package codesquad.team05.service;

import codesquad.team05.web.reservation.dto.request.ReservationServiceDto;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ReservationRedisService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ReservationService reservationService;


    public boolean createReservation(Long userId, Long accommodationId, ReservationServiceDto reservationServiceDto) {
        LocalDate checkIn = reservationServiceDto.getCheckIn();
        LocalDate checkOut = reservationServiceDto.getCheckOut();


        List<String> reservationDates = getDatesAsString(checkIn, checkOut);
        List<RLock> locks = new ArrayList<>();
        boolean allLocked = true;

        int size = reservationDates.size();

        try {
            for (int i = 0; i < size; i++) {
                String lockKey = "reservationLock:" + accommodationId + ":" + reservationDates.get(i).toString();
                RLock lock = redissonClient.getLock(lockKey);
                boolean sucess = lock.tryLock(5, 8, TimeUnit.SECONDS);

                if (!sucess) {
                    allLocked = false;
                    break;
                }
                locks.add(lock);
            }

            if (allLocked) {
                reservationService.createReservation(userId, accommodationId, reservationServiceDto);
                return true;
            } else {
                throw new IllegalStateException("Unable to lock all dates for reservation.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to acquire locks", e);
        } finally {

            // 모든 락을 해제한다.
            for (RLock lock : locks) {
                if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }

    public List<String> getDatesAsString(LocalDate checkIn, LocalDate checkOut) {
        List<String> dates = new ArrayList<>();
        while (!checkIn.isAfter(checkOut)) {
            dates.add(checkIn.toString());
            checkIn = checkIn.plusDays(1);
        }
        return dates;
    }
}
