package com.bc46.trabea.parttimeemployee.dto;

import com.bc46.trabea.parttimeemployee.Education;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartTimeEmployeeDetailResponse {
    private final String firstName;
    private final String lastName;
    private final String personalEmail;
    private final String workEmail;
    private final String personalPhoneNumber;
    private final Education lastEducation;
    private final Education onGoingEducation;
    private final LocalDateTime joinDate;
}
