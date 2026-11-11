
package com.project.cinema.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoNumberValidator
        implements ConstraintValidator<NoNumber, String> {

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ) {
        if (value == null) {
            return true;
        }

        return !value.matches(".*\\d.*");
    }
}
