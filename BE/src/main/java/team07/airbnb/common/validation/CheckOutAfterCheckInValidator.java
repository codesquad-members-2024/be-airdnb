package team07.airbnb.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import team07.airbnb.data.booking.dto.request.BookingRequest;

import java.time.LocalDate;

public class CheckOutAfterCheckInValidator implements ConstraintValidator<CheckOutAfterCheckIn, BookingRequest> {

    @Override
    public void initialize(CheckOutAfterCheckIn constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(BookingRequest request, ConstraintValidatorContext context) {
        LocalDate checkIn = request.checkIn();
        LocalDate checkOut = request.checkOut();

        if (checkIn == null || checkOut == null) {
            return true; // null 값은 다른 validator가 처리
        }

        return !checkOut.isBefore(checkIn);
    }
}
