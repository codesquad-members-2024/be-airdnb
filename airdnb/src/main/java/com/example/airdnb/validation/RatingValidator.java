package com.example.airdnb.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<ValidRating, Double> {

    @Override
    public void initialize(ValidRating constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Double rating, ConstraintValidatorContext constraintValidatorContext) {
        if (rating == null) {
            return true;
        }
        return rating >= 0.5 && rating <= 5.0 && rating % 0.5 == 0;
    }
}
