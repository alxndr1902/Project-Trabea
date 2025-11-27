package com.bc46.trabea.parttimeemployee.validator;

import com.bc46.trabea.parttimeemployee.PartTimeEmployeeService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UniquePersonalPhoneNumberValidator implements ConstraintValidator<UniquePersonalPhoneNumber, String> {
    private final PartTimeEmployeeService partTimeEmployeeService;

    @Override
    public boolean isValid(String input, ConstraintValidatorContext constraintValidatorContext) {
        if (input == null) {
            return true;
        }
        return partTimeEmployeeService.existsByPersonalPhoneNumber(input);
    }
}
