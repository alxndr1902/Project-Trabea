package com.bc46.trabea.parttimeemployee.dto;

import lombok.Data;

@Data
public class PartTimeEmployeeSummaryResponse {
    private final String fullName;
    private final String workEmail;
    private final String personalEmail;
    private final String personalPhoneNumber;
}
