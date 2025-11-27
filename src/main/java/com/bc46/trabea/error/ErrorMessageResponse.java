package com.bc46.trabea.error;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Data
@Builder
public class ErrorMessageResponse<T>{
    private final HttpStatus status;
    private final String message;
    private final T errors;
    private final ZonedDateTime timestamp = ZonedDateTime.now(ZoneId.of("Z"));
}
