
package com.project.cinema.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoNumberValidator.class)
@Target({
        ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.RECORD_COMPONENT
})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoNumber {

    String message() default "Tidak boleh mengandung angka";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
