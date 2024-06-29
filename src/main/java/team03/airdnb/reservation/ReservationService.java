package team03.airdnb.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.AccommodationRepository;
import team03.airdnb.exception.duplicate.DuplicateReservationException;
import team03.airdnb.exception.notFound.AccommodationNotFoundException;
import team03.airdnb.exception.notFound.UserNotFoundException;
import team03.airdnb.reservation.dto.request.ReservationSaveDto;
import team03.airdnb.user.User;
import team03.airdnb.user.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    public Long createReservation(ReservationSaveDto reservationSaveDto) {
        if (!reservationRepository.isReservationAvailable(reservationSaveDto.getAccommodationId(), reservationSaveDto.getCheckIn(), reservationSaveDto.getCheckOut())) { // 예약 중복 확인
            throw new DuplicateReservationException();
        }
        User user = userRepository.findById(reservationSaveDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Accommodation accommodation = accommodationRepository.findById(reservationSaveDto.getAccommodationId()).orElseThrow(AccommodationNotFoundException::new);
        Reservation savedReservation = reservationRepository.save(reservationSaveDto.toEntity(user, accommodation));
        return savedReservation.getId();
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    public boolean hasUserReservation(Long userId, Long accommodationId) {
        return reservationRepository.existsByUserAndAccommodation(userId, accommodationId);
    }
}
