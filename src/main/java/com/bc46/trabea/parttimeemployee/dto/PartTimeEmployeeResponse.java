package com.bc46.trabea.parttimeemployee.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PartTimeEmployeeResponse {
    private final String fullName;
    private final String personalEmail;
    private final String workEmail;
    private final String personalPhoneNumber;
    private final LocalDateTime joinDate;
}
