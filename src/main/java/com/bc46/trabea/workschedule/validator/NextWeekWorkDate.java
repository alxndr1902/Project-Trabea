package com.bc46.trabea.workschedule.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NextWeekWorkDateValidator.class)
public @interface NextWeekWorkDate {
    String message() default "Chosen Date Must be Date of Next Week";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
