package com.airdnb.clone.domain.booking;

import com.airdnb.clone.domain.booking.controller.request.BookingSaveRequest;
import com.airdnb.clone.domain.booking.controller.response.BookingResponse;
import com.airdnb.clone.domain.booking.entity.Booking;
import com.airdnb.clone.domain.booking.repository.BookingRepository;
import com.airdnb.clone.domain.member.entity.Member;
import com.airdnb.clone.domain.member.repository.MemberRepository;
import com.airdnb.clone.domain.stay.entity.Stay;
import com.airdnb.clone.domain.stay.repository.StayRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;
    private final StayRepository stayRepository;
    private final EntityManager em;

    @Transactional
    public BookingResponse create(BookingSaveRequest request) {

        // 요청한 예약이 예약 일정이 중복되면 예약이 불가
        Long bookedStayCount = bookingRepository.countBookedStay(request.getStayId(), request.getCheckIn(), request.getCheckOut());
        if (bookedStayCount > 0) {
            throw new IllegalArgumentException("예약 불가입니다.");
        }

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow();
        Stay findStay = stayRepository.findById(request.getStayId())
                .orElseThrow(() -> new IllegalArgumentException("숙소 못찾아요"));

        findStay.validateExceedGuest(request.getGuestCount());
        findStay.validateOpenStatus();

        Booking entity = Booking.builder()
                .checkIn(request.getCheckIn())
                .checkOut(request.getCheckOut())
                .member(member)
                .stay(findStay)
                .guestCount(request.getGuestCount())
                .totalRate(findStay.calculateTotalRate(request.getCheckIn(), request.getCheckOut()))
                .build();

        try {
            Booking saved = bookingRepository.save(entity);
            return BookingResponse.of(saved);
        } catch (PersistenceException error) {
            // TODO : 예외 처리 공부하기
//            PersistenceException: JPA에서 발생하는 예외의 상위 클래스입니다. 다양한 데이터베이스 관련 예외를 포괄합니다.
//            ConstraintViolationException: Hibernate에서 발생하는 제약 조건 위반 예외입니다.
//            DataAccessException: Spring Data JPA에서 발생하는 데이터 접근 예외의 상위 클래스입니다.
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    @Transactional
    public void edit(Long id, Integer guestCount) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow();

        booking.changeGuestCount(guestCount);
    }

    public BookingResponse findById(Long id) {
        return bookingRepository.findById(id)
                .map(BookingResponse::of)
                .orElseThrow();
    }
}
