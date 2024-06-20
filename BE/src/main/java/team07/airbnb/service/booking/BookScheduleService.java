package team07.airbnb.service.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import team07.airbnb.data.booking.enums.BookingStatus;
import team07.airbnb.entity.BookingEntity;
import team07.airbnb.repository.BookingRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookScheduleService {

    private final BookingRepository bookingRepository;

    /**
     * 정오마다 오늘이 체크아웃 날짜인 모든 예약을 이용 완료 상태로 변경
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void completeAllBookings() {
        LocalDate now = LocalDate.now();

        List<BookingEntity> bookings = bookingRepository.findAllByCheckout(now);

        bookings.forEach(booking -> {
            booking.setStatus(BookingStatus.COMPLETE);
            booking.getProducts().stream().close();
        });
    }
}
