package com.airdnb.clone.domain.booking;

import com.airdnb.clone.domain.booking.request.BookingSaveRequest;
import com.airdnb.clone.domain.booking.request.BookingUpdateRequest;
import com.airdnb.clone.domain.booking.response.BookingResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public BookingResponse create(@Valid @RequestBody BookingSaveRequest request) {
        return bookingService.create(request.toBuilder(), request.getStayId(), request.getMemberId());
    }

    @GetMapping("/{id}")
    public BookingResponse findBooking(@PathVariable("id") Long id) {
        return bookingService.findById(id);
    }

    @PatchMapping("/{id}")
    public void edit(@PathVariable("id") Long id, @Valid @RequestBody BookingUpdateRequest request) {
        bookingService.edit(id, request.toGuest());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        bookingService.delete(id);
    }
}
