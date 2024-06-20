package team07.airbnb.service.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team07.airbnb.data.booking.enums.CheckAuthType;
import team07.airbnb.data.user.dto.response.TokenUserInfo;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.exception.auth.UnAuthorizedException;
import team07.airbnb.repository.BookingRepository;
import team07.airbnb.service.user.UserService;

@Service
@RequiredArgsConstructor
public class BookingAuthService {

    private final BookingRepository bookingRepository;

    public void currentUserIsSameWith(Long bookingId, UserEntity user, CheckAuthType type) {
        boolean isPure = false;
        switch (type) {
            case ALL -> isPure = isUserHostOrBookerOf(bookingId, user);
            case HOST -> isPure = isUserHostOf(bookingId, user);
            case USER -> isPure = isUserBookerOf(bookingId, user);
        }

        if (!isPure) {
            throw new UnAuthorizedException(BookingAuthService.class, user.getId());
        }
    }

    private boolean isUserHostOrBookerOf(Long bookingId , UserEntity user){
        return isUserHostOf(bookingId, user) || isUserBookerOf(bookingId, user);
    }

    private boolean isUserHostOf(Long bookingId, UserEntity booker) {
        return bookingRepository.existsByIdAndBooker(bookingId, booker);
    }

    private boolean isUserBookerOf(Long bookingId, UserEntity host) {
        return bookingRepository.existsByIdAndHost(bookingId, host);
    }


}
