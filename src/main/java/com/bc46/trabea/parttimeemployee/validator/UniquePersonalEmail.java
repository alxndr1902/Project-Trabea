package com.bc46.trabea.parttimeemployee.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniquePersonalEmailValidator.class)
public @interface UniquePersonalEmail {
    String message() default "Email is unavailable";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
