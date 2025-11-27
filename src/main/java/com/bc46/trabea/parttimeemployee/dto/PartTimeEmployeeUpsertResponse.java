package com.bc46.trabea.parttimeemployee.dto;

import lombok.Data;

@Data
public class PartTimeEmployeeUpsertResponse {
    private final Integer id;
    private final String workEmail;
    private final String firstName;
    private final String lastName;
}
