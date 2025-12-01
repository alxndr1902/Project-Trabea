package com.bc46.trabea.auth.dto;

import lombok.Data;

@Data
public class AuthLoginRequest {
    private final String workEmail;
    private final String password;
    private final String role;
}

