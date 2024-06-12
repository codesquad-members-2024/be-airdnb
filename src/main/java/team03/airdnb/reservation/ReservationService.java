package team03.airdnb.reservation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.AccommodationRepository;
import team03.airdnb.reservation.dto.request.ReservationSaveDto;
import team03.airdnb.user.User;
import team03.airdnb.user.UserRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    public Reservation createReservation(ReservationSaveDto reservationSaveDto) {
        User user = userRepository.findById(reservationSaveDto.getUserId()).get(); // 예외처리 추가할 예정입니다!
        Accommodation accommodation = accommodationRepository.findById(reservationSaveDto.getAccommodationId()).get(); // 예외처리 추가할 예정입니다!
        return reservationRepository.save(reservationSaveDto.toEntity(user, accommodation));
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
