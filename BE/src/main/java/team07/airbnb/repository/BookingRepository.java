package team07.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team07.airbnb.entity.BookingEntity;
import team07.airbnb.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findAllByHost(UserEntity host);
    List<BookingEntity> findAllByBooker(UserEntity booker);

    boolean existsByIdAndBooker(Long bookingId, UserEntity booker);

    boolean existsByIdAndHost(Long bookingId, UserEntity host);

    List<BookingEntity> findAllByBooker(UserEntity booker);

    List<BookingEntity> findAllByCheckout(LocalDate checkOut);
}
