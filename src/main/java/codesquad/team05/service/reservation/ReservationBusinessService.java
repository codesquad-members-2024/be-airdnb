package codesquad.team05.service.reservation;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.accommodation.AccommodationRepository;
import codesquad.team05.domain.accommodation.reservation.Reservation;
import codesquad.team05.domain.accommodation.reservation.ReservationRepository;
import codesquad.team05.domain.exception.DateAlreadyBookedException;
import codesquad.team05.domain.exception.PersonCountExceededException;
import codesquad.team05.domain.user.User;
import codesquad.team05.domain.user.UserRepository;
import codesquad.team05.web.dto.request.reservation.ReservationServiceDto;
import codesquad.team05.web.dto.request.reservation.ReservationUpdate;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
public class ReservationBusinessService {
    private final AccommodationRepository accommodationRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public Long reservation(Long userId, Long accommodationId, ReservationServiceDto serviceDto) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();


        int amount = calculateAmount(serviceDto.getCheckIn(), serviceDto.getCheckOut(), accommodation.getPrice());

        Reservation reservation = serviceDto.toEntityForSave(amount);
        User user = userRepository.findById(userId).orElseThrow();

        validate(serviceDto, accommodation);

        reservation.setUser(user);
        reservation.setAccommodation(accommodation);

        return reservationRepository.save(reservation).getId();
    }

    public void update(Long reservationId, ReservationUpdate reservationUpdate) {
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow();
        reservation.update(reservationUpdate);

    }


    private int calculateAmount(LocalDate checkIn, LocalDate checkOut, int price) {


        int days = (int) ChronoUnit.DAYS.between(checkIn, checkOut);

        return days * price;
    }


    private void validate(ReservationServiceDto serviceDto, Accommodation accommodation) {
        if (serviceDto.getPersonCount() > accommodation.getMaxCapacity()) {
            throw new PersonCountExceededException("허용된 인원 수를 초과했습니다.");
        }

        if (!isAvailable(serviceDto.getCheckIn(), serviceDto.getCheckOut(), accommodation)) {
            throw new DateAlreadyBookedException("이미 예약된 상품입니다.");
        }
    }


    private boolean isAvailable(LocalDate checkIn, LocalDate checkOut, Accommodation accommodation) {


        List<Reservation> list = reservationRepository.findReservationByAccommodationId(accommodation.getId());

        //모든 예약이 시간이 겹치는지 아닌지 확인

        return list.stream().allMatch((i) -> {
            LocalDate reservationCheckIn = i.getCheckIn();
            LocalDate reservationCheckOut = i.getCheckOut();

            //새로운 예약의 체크인, 체크아웃이 예약의 체크아웃 이후거나
            //기존 예약의 체크인 이전이면 겹치지 않는 것으로 판단

            return checkOut.isBefore(reservationCheckIn) || checkIn.isAfter(reservationCheckOut);

        });

    }
}
