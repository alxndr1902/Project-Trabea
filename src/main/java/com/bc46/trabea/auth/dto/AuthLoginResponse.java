package com.bc46.trabea.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthLoginResponse {
    private final String token;
}
