package team07.airbnb.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Method;
import java.time.LocalDate;

public class CheckOutAfterCheckInValidator implements ConstraintValidator<CheckOutAfterCheckIn, Object> {

    @Override
    public void initialize(CheckOutAfterCheckIn constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        try {
            Method getCheckIn = object.getClass().getMethod("checkIn");
            Method getCheckOut = object.getClass().getMethod("checkOut");

            LocalDate checkIn = (LocalDate) getCheckIn.invoke(object);
            LocalDate checkOut = (LocalDate) getCheckOut.invoke(object);


            if (checkIn == null || checkOut == null) {
                return true; // null 값은 다른 validator가 처리
            }

            return !checkOut.isBefore(checkIn);
        }catch (Exception e) {
            throw new RuntimeException("Error validating CheckOutAfterCheckIn", e);
        }
    }
}
