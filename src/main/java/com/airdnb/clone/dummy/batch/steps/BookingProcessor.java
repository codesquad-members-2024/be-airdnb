package com.airdnb.clone.dummy.batch.steps;

import com.airdnb.clone.domain.booking.entity.Booking;
import com.airdnb.clone.domain.booking.entity.Booking.Status;
import com.airdnb.clone.domain.member.repository.MemberRepository;
import com.airdnb.clone.domain.stay.entity.Stay;
import com.airdnb.clone.dummy.RandomNumberGenerator;
import com.airdnb.clone.dummy.stay.DummyTravelerGenerator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@StepScope
public class BookingProcessor implements ItemProcessor<Stay, List<Booking>> {

    private static final AtomicInteger CURRENT_INDEX = new AtomicInteger(0);
    private static final int INCREMENT_STEP = 1;
    public static final int PEAK_SEASON_START = 6;
    public static final int PEAK_SEASON_END = 8;
    public static final int START_OF_MONTH = 1;
    public static final int END_OF_MONTH = 27;
    public static final int ONE_WEEK = 7;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public List<Booking> process(Stay stay) throws Exception {
        if (72_000 < CURRENT_INDEX.getAndAdd(INCREMENT_STEP) && CURRENT_INDEX.get() < 90_000) {
            return null; // 제주도 패스
        }
        if (138_000 < CURRENT_INDEX.get() && CURRENT_INDEX.get() < 150_000) {
            return null; // 부산 패스
        }
        if (198_000 < CURRENT_INDEX.get() && CURRENT_INDEX.get() < 210_000) {
            return null; // 강원 패스
        }
        if (282_000 < CURRENT_INDEX.get() && CURRENT_INDEX.get() < 300_000) {
            return null; // 전국 패스
        }

        List<Booking> bookings = new ArrayList<>();

        int step;
        for (int month = PEAK_SEASON_START; month <= PEAK_SEASON_END; month++) {
            for (int checkInDay = START_OF_MONTH; checkInDay <= END_OF_MONTH; checkInDay += step + START_OF_MONTH) {
                if (checkInDay % ONE_WEEK == 1) {
                    step = 2;
                } else {
                    step = 1;
                }
                int checkOutDay = checkInDay + step;
                LocalDateTime checkIn = LocalDate.of(2024, month, checkInDay).atTime((stay.getCheckInTime()));
                LocalDateTime checkOut = LocalDate.of(2024, month, checkOutDay).atTime((stay.getCheckOutTime()));

                Booking booking = createBooking(stay, checkIn, checkOut);

                bookings.add(booking);
            }
        }

        return bookings;
    }

    private Booking createBooking(Stay stay, LocalDateTime checkIn, LocalDateTime checkOut) {
        return Booking.builder()
                .member(memberRepository.findById(RandomNumberGenerator.generateLong(1, 25000)).orElseThrow())
                .stay(stay)
                .status(Status.RESERVED)
                .guestCount(DummyTravelerGenerator.generate())
                .totalRate(stay.getFee().calculateTotalRate(checkIn, checkOut))
                .checkIn(checkIn)
                .checkOut(checkOut)
                .build();
    }
}
