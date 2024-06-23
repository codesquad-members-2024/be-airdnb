package com.airbnb.global.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;

public class NoNullElementsValidator implements ConstraintValidator<NoNullElements, Collection<?>> {

    @Override
    public boolean isValid(Collection<?> collection, ConstraintValidatorContext context) {
        if (collection == null) {
            return true;  // 아예 빈 값은 허용함. 아니라면 @NotNull 설정
        }

        for (Object element : collection) {
            if (element == null) {
                return false;
            }
        }

        return true;
    }
}
