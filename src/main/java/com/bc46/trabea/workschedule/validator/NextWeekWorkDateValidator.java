package com.bc46.trabea.workschedule.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;

@RequiredArgsConstructor
public class NextWeekWorkDateValidator implements ConstraintValidator<NextWeekWorkDate, LocalDate> {

    private LocalDate mondayNextWeek;
    private LocalDate fridayNextWeek;

    @Override
    public void initialize(NextWeekWorkDate constraintAnnotation) {
        LocalDate now = LocalDate.now();
        mondayNextWeek = now.with(DayOfWeek.MONDAY).plusWeeks(1);
        fridayNextWeek = mondayNextWeek.plusDays(4);
    }

    @Override
    public boolean isValid(LocalDate input, ConstraintValidatorContext constraintValidatorContext) {
        if (input == null) {
            return true;
        }

        return !input.isBefore(mondayNextWeek) && !input.isAfter(fridayNextWeek);
    }
}
