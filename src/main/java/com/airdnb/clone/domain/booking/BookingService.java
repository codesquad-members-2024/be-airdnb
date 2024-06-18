package com.airdnb.clone.domain.booking;

import com.airdnb.clone.domain.booking.entity.Booking;
import com.airdnb.clone.domain.common.Guest;
import com.airdnb.clone.domain.member.entity.Member;
import com.airdnb.clone.domain.member.repository.MemberRepository;
import com.airdnb.clone.domain.booking.repository.BookingRepository;
import com.airdnb.clone.domain.booking.response.BookingResponse;
import com.airdnb.clone.domain.stay.entity.Stay;
import com.airdnb.clone.domain.stay.repository.StayRepository;
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

    @Transactional
    public BookingResponse create(Booking.BookingBuilder builder, Long stayId, Long hostId) {
        Member member = memberRepository.findById(hostId)
                .orElseThrow();
        Stay stay = stayRepository.findById(stayId)
                .orElseThrow();
        Booking entity = builder.member(member).stay(stay).build();
        Booking saved = bookingRepository.save(entity);
        return BookingResponse.of(saved);
    }

    @Transactional
    public void delete(Long id) {
        bookingRepository.deleteById(id);
    }

    @Transactional
    public void edit(Long id, Guest guest) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow();

        booking.changeGuest(guest);
    }

    public BookingResponse findById(Long id) {
        return bookingRepository.findById(id)
                .map(BookingResponse::of)
                .orElseThrow();
    }
}
