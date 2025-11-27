package com.bc46.trabea.parttimeemployee.dto;

import com.bc46.trabea.parttimeemployee.Education;
import com.bc46.trabea.parttimeemployee.validator.UniquePersonalEmail;
import com.bc46.trabea.parttimeemployee.validator.UniquePersonalPhoneNumber;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PartTimeEmployeeUpdateRequest {
    @NotBlank(message = "First Name cannot be empty")
    @Size(max = 25, message = "First Name exceeds maximum value (25)")
    private final String firstName;

    @NotBlank(message = "Last Name cannot be empty")
    @Size(max = 50, message = "Last Name exceeds maximum value (50)")
    private final String lastName;

    @NotBlank(message = "Personal Email cannot be empty")
    @Size(max = 100, message = "Personal Email exceeds maximum value (100)")
    @Email(message = "Invalid email format")
    private final String personalEmail;

    @NotBlank(message = "Phone Number cannot be empty")
    @Size(max = 20, message = "Phone Number exceeds maximum value (20)")
    @Pattern( regexp = "^[0-9]+$", message = "Phone Number must be numeric")
    private final String personalPhoneNumber;

    @NotNull(message = "Last Education cannot be empty")
    private final Education lastEducation;

    private final Education onGoingEducation;
}
